import { defineStore } from 'pinia';

/**
 * 修改配置时：
 * 1、需要每次都清理 `window.localStorage` 浏览器永久缓存
 * 2、或者点击布局配置最底部 `一键恢复默认` 按钮即可看到效果
 */
export const useThemeConfig = defineStore('themeConfig', {
	state: (): ThemeConfigState => ({
		themeConfig: 
		{
			isDrawer: false,
			primary: "#2048D9",
			isIsDark: false,
			topBar: "#C9CAF8",
			topBarColor: "#120314",
			isTopBarColorGradual: false,
			menuBar: "#FFFFFF",
			menuBarColor: "#505968",
			menuBarActiveColor: "rgba(242, 243, 245, 1)",
			isMenuBarColorGradual: true,
			columnsMenuBar: "#545c64",
			columnsMenuBarColor: "#e6e6e6",
			isColumnsMenuBarColorGradual: false,
			isColumnsMenuHoverPreload: false,
			isCollapse: false,
			isUniqueOpened: true,
			isFixedHeader: false,
			isFixedHeaderChange: false,
			isClassicSplitMenu: false,
			isLockScreen: false,
			lockScreenTime: 30,
			isShowLogo: true,
			isShowLogoChange: false,
			isBreadcrumb: true,
			isTagsview: true,
			isBreadcrumbIcon: false,
			isTagsviewIcon: false,
			isCacheTagsView: true,
			isSortableTagsView: true,
			isShareTagsView: false,
			isFooter: true,
			isGrayscale: false,
			isInvert: false,
			isWartermark: false,
			wartermarkText: "Car",
			tagsStyle: "tags-style-five",
			animation: "slide-left",
			columnsAsideStyle: "columns-round",
			columnsAsideLayout: "columns-vertical",
			layout: "transverse",
			isRequestRoutes: true,
			globalTitle: "智慧停车管理系统",
			globalViceTitle: "智慧停车管理系统",
			globalViceTitleMsg: "高效、方便、美观",
			globalI18n: "zh-cn",
			globalComponentSize: "small",
			footerAuthor: "©2024 管理api"
			}
	}),
	actions: {
		setThemeConfig(data: ThemeConfigState) {
			this.themeConfig = data.themeConfig;
		},
	},
});
