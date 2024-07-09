package main

import (
	"context"
	"embed"
	"fixjson/internal/service"
	"github.com/wailsapp/wails/v2"
	"github.com/wailsapp/wails/v2/pkg/menu"
	"github.com/wailsapp/wails/v2/pkg/options"
	"github.com/wailsapp/wails/v2/pkg/options/assetserver"
	"github.com/wailsapp/wails/v2/pkg/options/windows"
)

//go:embed all:frontend/dist
var assets embed.FS
var version = "0.0.0"

func main() {
	preference := service.Preference()

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
		OnStartup: func(ctx context.Context) {
			preference.SetAppVersion(version)
		},
		Bind: []interface{}{
			preference,
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
