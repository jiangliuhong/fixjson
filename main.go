package main

import (
	"embed"
	"github.com/wailsapp/wails/v2"
	"github.com/wailsapp/wails/v2/pkg/menu"
	"github.com/wailsapp/wails/v2/pkg/options"
	"github.com/wailsapp/wails/v2/pkg/options/assetserver"
	"github.com/wailsapp/wails/v2/pkg/options/windows"
)

//go:embed all:frontend/dist
var assets embed.FS

func main() {
	// Create an instance of the app structure
	app := NewApp()

	// Create application with options
	err := wails.Run(&options.App{
		Title:  "FixJSON",
		Width:  1024,
		Height: 768,
		Menu:   menu.NewMenu(),
		AssetServer: &assetserver.Options{
			Assets: assets,
		},
		Frameless:                true,
		EnableDefaultContextMenu: true,
		BackgroundColour:         options.NewRGBA(27, 38, 54, 0),
		OnStartup:                app.startup,
		Bind: []interface{}{
			app,
		},
		Windows: &windows.Options{
			WebviewIsTransparent:              true,
			WindowIsTranslucent:               true,
			DisableFramelessWindowDecorations: true,
		},
	})

	if err != nil {
		println("Error:", err.Error())
	}
}
