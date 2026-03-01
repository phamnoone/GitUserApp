# GitUserApp
This app create for show list users from GitHub


## 開発ワークフロー (Workflow)
本プロジェクトは以下のステップで進行します。
1. 要件分析
2. 設計および技術選定
3. タスク作成
4. プロジェクト初期化
5. コーディング
6. テスト
7. Readme作成とDemo


## 要件分析
GitHub API からデータを取得し、そのデータをアプリに表示する。
### 画面一覧と機能
1.ユーザー覧画面
 - 検索画面
 - リスト表示
 - 詳細画面遷移
 -  ページネーション
2.ユーザー詳細画面
- ユーザー情報の表示
- WebViewで外部リンクの表示

### UI/UX
Github UIを参考

<img width="430" height="939" alt="image" src="https://github.com/user-attachments/assets/80d42515-32e4-47d2-9706-393b8d87d2eb" />
<img width="428" height="933" alt="image" src="https://github.com/user-attachments/assets/8275b7ad-b552-4170-8815-d08cf0b52d00" />


### API 
GithubAPI documents　https://docs.github.com/en/rest

## 設計および技術選定
- Pattern: MVI + Clean Architecture
- Dependency Injection: Hilt
- Kotlin
- Jetpack Compose
- Networking: Retrofit + OkHttp
- Image Loading	Coil

### Project Structure: 
TODO 

### Data Flow : 
TODO
