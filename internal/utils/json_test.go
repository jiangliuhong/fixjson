package utils

import (
	"bytes"
	"encoding/json"
	"fmt"
	"strings"
	"testing"
)

func formatJSON(input string) (string, error) {
	var buf bytes.Buffer
	if err := json.Indent(&buf, []byte(input), "", "  "); err != nil {
		return "", err
	}
	return buf.String(), nil
}

func TestFormatJson(t *testing.T) {
	// 示例 JSON 字符串
	input := `{"name":"John", 
"age":30 "city":"New York"}`

	formatted, err := formatJSON(input)
	if err != nil {
		fmt.Println("JSON 格式错误:", err)
		fmt.Println("错误位置:", findErrorPosition(input, err))
	} else {
		fmt.Println("格式化后的 JSON:")
		fmt.Println(formatted)
	}
}

// findErrorPosition 查找 JSON 错误位置
func findErrorPosition(input string, err error) int {
	// 获取错误信息中的位置
	if syntaxError, ok := err.(*json.SyntaxError); ok {
		pos := int(syntaxError.Offset)
		if pos > 0 && pos <= len(input) {
			// 计算错误的行和列位置
			line := strings.Count(input[:pos], "\n") + 1
			lastLineIndex := strings.LastIndex(input[:pos], "\n")
			column := pos
			if lastLineIndex != -1 {
				column = pos - lastLineIndex
			}
			fmt.Printf("错误行: %d, 错误列: %d\n", line, column)
			return pos
		}
	}
	return -1
}
