import {defineStore} from 'pinia'

/**
 * connection dialog type
 * @enum {number}
 */
export const ConnDialogType = {
    NEW: 0,
    EDIT: 1,
}

const useDialogStore = defineStore('dialog', {
    state: () => ({
        aboutDialogVisible: false,
    }),
    actions:{
        openAboutDialog() {
            this.aboutDialogVisible = true
        },
        closeAboutDialog() {
            this.aboutDialogVisible = false
        },
    }
})

export default useDialogStore
