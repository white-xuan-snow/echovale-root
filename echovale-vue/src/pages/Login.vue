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
          <v-form ref="form" v-model="isFormValid" @submit.prevent="handleLogin">
            <v-text-field
              v-model="username"
              label="用户名"
              prepend-inner-icon="mdi-account"
              :rules="[rules.required]"
              variant="outlined"
              density="compact"
            ></v-text-field>

            <v-text-field
              v-model="password"
              label="密码"
              prepend-inner-icon="mdi-lock"
              :type="showPassword ? 'text' : 'password'"
              :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
              :rules="[rules.required, rules.minLength(6)]"
              variant="outlined"
              density="compact"
              @click:append-inner="showPassword = !showPassword"
            ></v-text-field>

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

    <!-- 步骤2: 替换为新的滑块验证组件 -->
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
// 步骤1: 导入新的验证码组件
import CaptchaDialog from '../components/CaptchaDialog.vue';

gsap.registerPlugin(CSSPlugin);

// -- 响应式状态和引用 --

const loginCard = ref<any>(null);
const form = ref<any>(null);
const username = ref('');
const password = ref('');
const showPassword = ref(false);
const isFormValid = ref(false);
// 步骤3: 重命名 state，用于控制新的验证码对话框
const captchaDialog = ref(false);
const router = useRouter();

// -- 响应式计算属性 --
const isMobileMode = computed(() => {
  return isMobile().any;
});

// -- 表单校验规则 --
const rules = {
  required: (value: string) => !!value || '此项不能为空.',
  minLength: (length: number) => (value: string) => (value && value.length >= length) || `密码长度至少为 ${length} 位.`,
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


// -- 逻辑处理函数 --

// 步骤4: 更新 handleLogin 逻辑
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

// 步骤5: 此函数现在由 CaptchaDialog 的 'success' 事件触发
const handleVerificationSuccess = () => {
  // 确保对话框已关闭
  captchaDialog.value = false;
  
  // 播放退出动画并执行后续操作
  playExitAnimation(() => {
    const fakeToken = 'fake_jwt_token_' + Date.now();
    localStorage.setItem('token', fakeToken);
    router.push('/home');
  });
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
</style>
