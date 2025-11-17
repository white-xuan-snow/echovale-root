import {defineStore} from "pinia";
import {ref, type Ref} from "vue";
// 假设已安装 lodash，用于防抖 (debounce)
import {debounce} from "lodash";

export const useWindowStore = defineStore("windowStore", () => {

    // 初始化时获取当前尺寸
    const innerWidth: Ref<number> = ref(window.innerWidth);
    const innerHeight: Ref<number> = ref(window.innerHeight);

    // 新增状态：是否为竖屏模式。初始计算一次。
    const isPortraitMode: Ref<boolean> = ref(window.innerHeight / window.innerWidth > 1.25);

    // 核心函数：更新尺寸和模式
    const updateDimensions = () => {
        innerWidth.value = window.innerWidth;
        innerHeight.value = window.innerHeight;
        // 竖屏激活比例为长/宽=0.8（即高度/宽度 > 1.25）
        isPortraitMode.value = innerHeight.value / innerWidth.value > 1.25;
        console.log("[windowStore].[updateDimensions] 窗口尺寸:", innerWidth.value, "x", innerHeight.value, "竖屏模式:", isPortraitMode.value, "比例:", (innerHeight.value / innerWidth.value).toFixed(2));
    }

    // 使用 lodash 的 debounce
    const handleResize = debounce(updateDimensions, 100);

    function init() {
        // 立即执行一次以确保初始值正确
        updateDimensions();

        // 监听窗口 resize 事件
        window.addEventListener("resize", handleResize);

        // 返回清理函数，以便在需要时移除监听器
        return () => {
            window.removeEventListener("resize", handleResize);
        }
    }


    return { innerWidth, innerHeight, isPortraitMode, init };
})