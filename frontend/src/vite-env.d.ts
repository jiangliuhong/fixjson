/// <reference types="vite/client" />

declare module '*.vue' {
    import type {DefineComponent} from 'vue'
    const component: DefineComponent<{}, {}, any>
    export default component
}

// declare module '@json-editor/json-editor'{
//     export default class JSONEditor {
//         constructor(element: HTMLElement, options: any);
//         getValue(): any;
//         setValue(value: any): void;
//         destroy(): void;
//     }
// }