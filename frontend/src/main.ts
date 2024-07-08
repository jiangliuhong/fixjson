import { createPinia } from 'pinia'
import {createApp} from 'vue'
import App from './App.vue'
import './styles/style.scss';

async function run() {
    const app = createApp(App)
    app.use(createPinia())
    app.mount('#app')
}

run().then(() => console.log("load success!!"))