import {enUS, useOsTheme, zhCN} from 'naive-ui'
import {get, isEmpty, join, map, split} from 'lodash'
import {defineStore} from 'pinia'

const osTheme = useOsTheme()
const usePreferencesStore = defineStore('preferences', {
    state: () => ({
        general: {
            theme: 'auto',
            language: 'auto',
            font: '',
            fontFamily: [],
            fontSize: 14,
            scanSize: 3000,
            keyIconStyle: 0,
            useSysProxy: false,
            useSysProxyHttp: false,
            checkUpdate: true,
            skipVersion: '',
            allowTrack: true,
        }
    }),
    getters: {
        themeLocale() {
            const lang = this.currentLanguage
            switch (lang) {
                case 'zh':
                    return zhCN
                default:
                    return enUS
            }
        },

        /**
         * get current language setting
         * @return {string}
         */
        currentLanguage(): string {
            let lang: any = get(this.general, 'language', 'auto')
            if (lang === 'auto') {
                // const systemLang = navigator.language || navigator.userLanguage
                const systemLang = navigator.language
                lang = split(systemLang, '-')[0]
            }
            return lang || 'en'
        },

        isDark() {
            const th: any = get(this.general, 'theme', 'auto')
            if (th !== 'auto') {
                return th === 'dark'
            } else {
                return osTheme.value === 'dark'
            }
        },

        generalFont(): any {
            const fontStyle = {
                fontSize: this.general.fontSize + 'px',
                fontFamily: ''
            }
            if (!isEmpty(this.general.fontFamily)) {
                fontStyle['fontFamily'] = join(
                    map(this.general.fontFamily, (f) => `"${f}"`),
                    ',',
                )
            }
            return fontStyle
        },
    },
    actions: {}
})

export default usePreferencesStore
