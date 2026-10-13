# Kotlin LINE MINI App

可編譯、可啟動的 Kotlin 全端骨架。以模組化單體起步，隨實際業務逐步加入 DDD 與六邊形架構。

## 工具鏈

- JDK 25
- Spring Boot 4.1.1
- Kotlin 2.3.21（對齊 Spring Boot 管理版本）
- Gradle Wrapper 9.3.0
- Kotlin/JS Browser 與官方 Webpack 工具鏈

安裝 JDK 25 並設定 `JAVA_HOME`。不需要全域 Gradle、Node.js 或套件管理器；Gradle 會下載固定版本的建置工具與依賴，首次建置需要網路。

## 建置與執行

Windows PowerShell：

```powershell
.\gradlew.bat build
.\gradlew.bat :apps:server:bootRun
# 在另一個終端執行：
.\gradlew.bat :apps:miniapp:jsBrowserDevelopmentRun
```

macOS / Linux 使用相同參數執行 `./gradlew`。

後端預設使用 `http://localhost:8080`，尚無業務端點，根路徑回應 404。
前端開發伺服器預設使用 `http://localhost:8081`，只載入空白 HTML 與 Kotlin/JS bundle。

產物：

- 後端：`apps/server/build/libs/server.jar`
- 前端：`apps/miniapp/build/dist/js/productionExecutable/`

目前沒有登入、LIFF 呼叫、Rich Menu 建立、資料庫或業務 API。CI 在 Linux 與 Windows 驗證建置與後端啟動，不代表已完成 LINE 平台或手機端整合驗證。

後端包含 HTTP 啟動測試；前端尚無行為測試，第一個前端行為加入時再啟用 browser test runner。`kotlin-js-store/package-lock.json` 由 Gradle 管理並納入版本控制，固定 npm 相依版本。

## 結構與演進

```text
apps/
  server/       Kotlin/JVM、Spring Boot、bootstrap
  miniapp/      Kotlin/JS、空白 Web 外殼
docs/
  architecture.md
gradle/
  libs.versions.toml
  wrapper/
```

從第一個實際業務能力開始建立限界上下文與架構檢查；每次只堆疊必要功能。
架構邊界與 LINE 入口契約見 [架構說明](docs/architecture.md)。
