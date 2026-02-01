<template>
  <div class="login-background">
    <div class="login-container">
      <v-card
        ref="loginCard"
        class="glass-card"
        :width="isMobileMode ? '85%' : '400'"
        style="opacity: 0; transform: translateY(100px);"
        elevation="8"
      >
        <v-card-title class="text-center text-h5 font-weight-bold py-4">
          欢迎回来
        </v-card-title>
        <v-card-text class="py-6">
          <!-- 登录方式切换 -->
          <v-tabs v-model="activeTab" class="mb-4">
            <v-tab value="password">密码登录</v-tab>
            <v-tab value="captcha">验证码登录</v-tab>
          </v-tabs>

          <v-form ref="form" v-model="isFormValid" @submit.prevent="handleLogin">
            <v-text-field
              v-model="identifier"
              label="用户名/手机号/邮箱"
              :rules="[rules.required, getIdentifierRules]"
              variant="outlined"
              density="compact"
              @input="detectInputType"
            ></v-text-field>

            <!-- 密码字段（仅密码登录方式显示） -->
            <v-text-field
              v-if="activeTab === 'password'"
              v-model="credential"
              label="密码"
              :type="showPassword ? 'text' : 'password'"
              :rules="[rules.required, rules.minLength(6)]"
              variant="outlined"
              density="compact"
              @click:append-inner="showPassword = !showPassword"
            ></v-text-field>

            <!-- 验证码字段（仅验证码登录方式显示） -->
            <div v-else class="d-flex gap-2">
              <v-text-field
                v-model="credential"
                label="验证码"
                :rules="[rules.required, rules.captchaLength]"
                variant="outlined"
                density="compact"
                class="flex-grow-1"
              ></v-text-field>
              <v-btn
                :disabled="countdown > 0"
                color="primary"
                variant="outlined"
                density="compact"
                @click="sendCaptcha"
                class="align-self-center"
              >
                {{ countdown > 0 ? `${countdown}s后重发` : '发送验证码' }}
              </v-btn>
            </div>

            <v-btn
              type="submit"
              block
              color="primary"
              class="mt-2"
              size="large"
            >
              登录
            </v-btn>
          </v-form>
        </v-card-text>

        <div class="d-flex justify-space-between align-center px-4 pb-4">
          <a href="#" class="text-caption link">忘记密码?</a>
          <a href="#" class="text-caption link">立即注册</a>
        </div>
      </v-card>
    </div>

    <!-- 滑块验证组件 -->
    <captcha-dialog 
      v-model="captchaDialog" 
      @success="handleVerificationSuccess"
    ></captcha-dialog>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { gsap } from 'gsap';
import { CSSPlugin } from 'gsap/CSSPlugin';
import isMobile from 'ismobilejs';
import { ElMessage } from 'element-plus';
import CaptchaDialog from '../components/CaptchaDialog.vue';

gsap.registerPlugin(CSSPlugin);

// -- 响应式状态和引用 --

const loginCard = ref<any>(null);
const form = ref<any>(null);
const identifier = ref(''); // 用户名/手机号/邮箱
const credential = ref(''); // 密码/验证码
const showPassword = ref(false);
const isFormValid = ref(false);
const captchaDialog = ref(false);
const router = useRouter();

// 登录方式相关状态
const activeTab = ref('password'); // 'password' 或 'captcha'

// 输入类型检测相关状态
const inputType = ref('username'); // 'username', 'phone', 'email'
const detectTimeout = ref<number | null>(null); // 输入检测防抖定时器ID

// 检测输入类型的正则表达式
const phoneRegex = /^1[3-9]\d{9}$/;
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

// 验证码发送相关状态
const countdown = ref(0);
const validToken = ref(''); // 滑块验证成功后的 Secondary-Token

// -- 响应式计算属性 --
const isMobileMode = computed(() => {
  return isMobile().any;
});

// -- 表单校验规则 --
const rules = {
  required: (value: string) => !!value || '此项不能为空.',
  minLength: (length: number) => (value: string) => (value && value.length >= length) || `长度至少为 ${length} 位.`,
  phone: (value: string) => /^1[3-9]\d{9}$/.test(value) || '请输入有效的手机号.',
  email: (value: string) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value) || '请输入有效的邮箱地址.',
  captchaLength: (value: string) => value.length === 6 || '验证码长度为6位.',
};

// -- GSAP 动画定义 --

const playShakeAnimation = () => {
  if (!loginCard.value?.$el) return;
  
  const tl = gsap.timeline();
  tl.to(loginCard.value.$el, {
    x: -10,
    duration: 0.1,
  }).to(loginCard.value.$el, {
    x: 10,
    duration: 0.1,
    repeat: 2,
    yoyo: true,
  }).to(loginCard.value.$el, {
    x: 0,
    duration: 0.1,
  });
};

const playExitAnimation = (onComplete: () => void) => {
  if (!loginCard.value?.$el) return;

  const tl = gsap.timeline({
    onComplete,
  });

  tl.to(loginCard.value.$el, {
    scale: 1.2,
    opacity: 0,
    duration: 0.5,
    ease: 'power2.in',
  });
};

// -- 输入类型检测相关方法 --

// 检测输入内容的类型
const detectInputType = () => {
  // 清除之前的定时器
  if (detectTimeout.value) {
    clearTimeout(detectTimeout.value);
  }
  
  // 设置新的定时器，2秒后执行检测
  detectTimeout.value = window.setTimeout(() => {
    const value = identifier.value.trim();
    
    if (phoneRegex.test(value)) {
      inputType.value = 'phone';
    } else if (emailRegex.test(value)) {
      inputType.value = 'email';
    } else {
      inputType.value = 'username';
    }
    
    // 如果输入的是用户名，则不允许使用验证码登录
    if (inputType.value === 'username' && activeTab.value === 'captcha') {
      activeTab.value = 'password';
    }
    
    // 清除定时器ID
    detectTimeout.value = null;
  }, 2000);
};

// 根据输入类型返回相应的验证规则
const getIdentifierRules = (value: string) => {
  if (!value) return true; // 必填规则已经处理
  
  switch (inputType.value) {
    case 'phone':
      return rules.phone(value);
    case 'email':
      return rules.email(value);
    case 'username':
    default:
      return true; // 用户名没有额外的验证规则
  }
};

// -- 验证码发送相关方法 --

const sendCaptcha = () => {
  // 验证 identifier 格式是否正确
  let isValid = false;
  if (activeTab.value === 'captcha') {
    if (inputType.value === 'phone') {
      isValid = Boolean(rules.phone(identifier.value));
    } else if (inputType.value === 'email') {
      isValid = Boolean(rules.email(identifier.value));
    }
  }

  if (!isValid) {
    playShakeAnimation();
    return;
  }

  // 模拟发送验证码请求
  console.log(`发送验证码到 ${identifier.value}`);
  
  // 开始倒计时
  countdown.value = 60;
  const timer = setInterval(() => {
    countdown.value--;
    if (countdown.value <= 0) {
      clearInterval(timer);
    }
  }, 1000);
};

// -- 逻辑处理函数 --

const handleLogin = async () => {
  const { valid } = await form.value.validate();

  if (valid) {
    // 表单验证通过，打开滑块验证对话框
    captchaDialog.value = true;
  } else {
    // 验证失败，播放抖动动画
    playShakeAnimation();
  }
};

const handleVerificationSuccess = (token: string) => {
  // 确保对话框已关闭
  captchaDialog.value = false;
  // 保存 Secondary-Token
  validToken.value = token;
  
  // 确定 loginType
  let loginType = '';
  if (activeTab.value === 'password') {
    switch (inputType.value) {
      case 'username':
        loginType = 'PASSWORD_USERNAME';
        break;
      case 'phone':
        loginType = 'PASSWORD_PHONE';
        break;
      case 'email':
        loginType = 'PASSWORD_EMAIL';
        break;
    }
  } else {
    switch (inputType.value) {
      case 'phone':
        loginType = 'CAPTCHA_PHONE';
        break;
      case 'email':
        loginType = 'CAPTCHA_EMAIL';
        break;
    }
  }

  // 发送登录请求
  loginRequest(loginType);
};

// 登录请求函数
const loginRequest = async (loginType: string) => {
  try {
    // 构建请求参数
    const params = {
      identifier: identifier.value,
      credential: credential.value,
      loginType: loginType,
    };

    // 构建请求头
    const headers = {
      'Secondary-Token': validToken.value,
    };

    // 将参数转换为URL查询字符串
    const queryString = new URLSearchParams(params).toString();
    const url = `/api/v1/auth/login?${queryString}`;

    // 发送登录请求
    console.log('登录请求URL:', url);
    console.log('登录请求头:', headers);
    
    // 发送GET请求（使用URL参数）
    const response = await fetch(url, {
      method: 'GET',
      headers: headers,
    });

    // 无论成功与否，单次验证 token 通常失效
    validToken.value = '';

    // 解析响应数据
    let responseData;
    try {
      responseData = await response.json();
    } catch (e) {
      throw new Error(`请求失败: ${response.statusText}`);
    }

    console.log('登录响应:', responseData);

    // 检查响应状态和业务状态 (适配后端返回的 code: 200)
    if (response.ok && (responseData.code === 200 || responseData.success)) {
      // 播放退出动画并执行后续操作
      playExitAnimation(() => {
        // 保存 Token (适配后端返回的 accessToken 字段)
        const token = responseData.data.accessToken || responseData.data.token;
        if (token) {
          // 在 token 开头加入 Bearer 前缀，符合 Authorization Header 规范
          localStorage.setItem('token', `Bearer ${token}`);
        }
        router.push('/home');
      });
    } else {
      // 优先使用后端返回的 msg
      const errorMsg = responseData.msg || response.statusText || '登录失败';
      ElMessage.error(errorMsg);
      playShakeAnimation();
    }
  } catch (error: any) {
    console.error('登录失败:', error);
    ElMessage.error(error.message || '网络请求失败');
    playShakeAnimation();
  }
};

// -- 生命周期钩子 --

onMounted(() => {
  if (!loginCard.value?.$el) return;

  gsap.fromTo(
    loginCard.value.$el,
    {
      opacity: 0,
      y: 100,
    },
    {
      opacity: 1,
      y: 0,
      duration: 0.8,
      ease: 'expo.out',
      delay: 0.2,
    }
  );
});
</script>

<style scoped>
.login-background {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url('https://images.unsplash.com/photo-1501785888041-af3ef285b470?q=80&w=2070&auto=format&fit=crop');
  background-size: cover;
  background-position: center;
}

.login-container {
  width: 100%;
  padding: 16px;
  display: flex;
  justify-content: center;
}

.glass-card {
  backdrop-filter: blur(16px) saturate(180%);
  -webkit-backdrop-filter: blur(16px) saturate(180%);
  background-color: rgba(255, 255, 255, 0.1) !important;
  border: 1px solid rgba(255, 255, 255, 0.2) !important;
  border-radius: 12px !important;
  transition: width 0.3s ease, height 0.3s ease;
}

.link {
  color: #E0E0E0;
  text-decoration: none;
  transition: color 0.3s;
}

.link:hover {
  color: #FFFFFF;
}

:deep(.v-text-field .v-label) {
  color: rgba(255, 255, 255, 0.7) !important;
}

:deep(.v-text-field input) {
  color: #FFFFFF !important;
}

:deep(.v-text-field .v-icon) {
  color: rgba(255, 255, 255, 0.7) !important;
}

/* 修复按钮聚焦时的白色边缘问题 */
:deep(.v-btn:focus) {
  outline: none !important;
  box-shadow: none !important;
}

:deep(.v-tab:focus) {
  outline: none !important;
  box-shadow: none !important;
}
</style>
