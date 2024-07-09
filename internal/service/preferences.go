package service

import (
	"fixjson/internal/types"
	"strings"
	"sync"
)

type preferencesService struct {
	clientVersion string
}

var preferences *preferencesService
var oncePreferences sync.Once

func Preference() *preferencesService {
	if preferences == nil {
		oncePreferences.Do(func() {
			preferences = &preferencesService{
				clientVersion: "",
			}
		})
	}
	return preferences
}

func (p *preferencesService) SetAppVersion(ver string) {
	if !strings.HasPrefix(ver, "v") {
		p.clientVersion = "v" + ver
	} else {
		p.clientVersion = ver
	}
}

func (p *preferencesService) GetClientVersion() (resp types.JSResp) {
	resp.Success = true
	resp.Data = map[string]any{
		"version": p.clientVersion,
	}
	return
}
