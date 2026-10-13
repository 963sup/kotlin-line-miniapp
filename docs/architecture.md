# 架構基線

## 起點

採模組化單體：一個 Kotlin 後端與一個 Kotlin/JS Web 前端。先讓建置、啟動與交付可靠，再按實際業務增加能力。

本骨架不預先建立領域模組。Enterprise、Organization、Team、Repository 等協作模型只是未來建模的參考，不據此宣告核心子域、限界上下文或聚合。

## 新增第一個能力

先確認業務語言、Owner、資料權威與一致性要求，再建立對應上下文。後端以 `dev.kotlinlineminiapp.<context>` 為模組根套件，採以下結構：

```text
<context>/
  api/                  其他上下文可使用的公開契約
  domain/               純 Kotlin 的模型、規則與領域事件
  application/          Use Case 與輸入／輸出 Port
  adapter/inbound/      HTTP 或其他外部輸入
  adapter/outbound/     持久化、LINE 與其他外部服務
bootstrap/              應用啟動、設定與依賴組裝；位於上下文之外
```

- Domain 不依賴 Spring、HTTP、資料庫、LINE SDK 或其他上下文的內部實作。
- Application 協調 Use Case、交易與所需 Port，不直接呼叫外部 SDK。
- Adapter 呼叫輸入 Port 或實作輸出 Port；Bootstrap 負責組裝。
- 對外契約使用明確的輸入／輸出型別，不暴露聚合、持久化實體或供應商 DTO。
- 跨上下文預設使用單體內的同步契約。只有需要業務事件時才加入事件；資料寫入與聚合交易由各自 Owner 管理。
- Owner 分離不阻斷能力協作；避免共用資料表、跨模組 Repository 存取或萬用 `common` 業務模型。

第一個領域模組加入時，同步加入 Spring Modulith 驗證無循環、公開契約與允許的依賴，並驗證 Domain／Application 的依賴方向。目前只有 bootstrap，尚無可驗證的業務模組邊界，不以空檢查宣稱架構已受強制保護。

每個能力用 Actor、Object、Action、Event、Relationship、Permission、State 七個面向描述必要規則；這是分析方式，不建立七套通用框架。Entity、Value Object、Aggregate、Domain Service、Domain Event、Repository 與 Factory 均依實際需求引入。

## LINE 與 Rich Menu 入口契約

```text
LINE 官方帳號 Rich Menu
  → URI action：MINI App 永久連結
  → Kotlin/JS Web 入口
  → 前端 LINE adapter / LIFF
  → 後端 inbound adapter → Application → Domain
```

- 預設入口為 `https://miniapp.line.me/{liffId}/`；`liffId` 由對應環境的 LINE MINI App channel 提供。未來子路由附加在相同永久連結後，並配置 Web hosting 的入口回退。
- 前端負責入口導覽與 LIFF 整合；Rich Menu 的內容、發布與綁定由 LINE 官方帳號整合 Owner 負責，不作為核心領域。
- 本階段僅記錄契約，不加入 LIFF 依賴、登入、路由功能、Rich Menu 資源或 Messaging API 呼叫。
- 未來加入身分功能時，由後端驗證 LINE token；不信任前端傳入的 user ID 或解碼 profile。身分驗證與業務授權分開，點擊選單或知道 URL 不授予資源存取權。
- LINE MINI App channel 設定與 Messaging API channel 憑證分開管理，伺服器密鑰不得打包進 Kotlin/JS。

## 漸進演進

先完成一個實際 Use Case 及其契約、測試，再增加下一個能力。CQRS、Outbox、Saga、Event Sourcing、Microservices 依實際問題評估，沒有固定導入順序。

參考：

- [Kotlin/JS 專案設定](https://kotlinlang.org/docs/js-project-setup.html)
- [Spring Boot Kotlin 支援](https://docs.spring.io/spring-boot/reference/features/kotlin.html)
- [Spring Modulith 邊界驗證](https://docs.spring.io/spring-modulith/reference/verification.html)
- [LINE MINI App 永久連結](https://developers.line.biz/en/docs/line-mini-app/develop/permanent-links/)
- [LIFF 伺服器使用者資料驗證](https://developers.line.biz/en/docs/liff/using-user-profile/)
