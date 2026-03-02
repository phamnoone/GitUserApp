# GitUserApp
This app create for show list users from GitHub

## Demo video 
[Screen_recording_20260302_181147.webm](https://github.com/user-attachments/assets/5eeb488c-a6a3-4201-b1b0-eb7bbb42672c)


## 開発ワークフロー (Workflow)
本プロジェクトは以下のステップで進行します。
1. 要件分析
2. 設計および技術選定
3. タスク作成
4. プロジェクト初期化
5. コーディング
6. テスト
7. Readme作成とDemo


### タスク作成

**Main Tasks**

DONE : ~~task-1: Initialize project~~

DONE : ~~task-2: Integrate core libraries: Hilt, Retrofit, Coroutines, Coil...~~

DONE : ~~task-3: Implement Base Application, Navigation~~

DONE : ~~task-4: Build Domain Layer~~

DONE : ~~task-5: Implement Data Layer (API Services, DTOs, Mappers, and Repository impl).~~

DONE : ~~task-6: User List Screen~~

DONE : ~~task-7: User Detail Screen feature~~

DONE :~~task-8: Write Unit Tests~~

task-9: Write E2E Tests(Need fix error)

task-10: Finalize documentation and Demo video.

**Kaizen Tasks (Bonus Excellence)** If has more time

kaizen-1: Implement Offline-first using Room Database

kaizen-2: Support Dark Mode

kaizen-3: CI/CD Pipeline using GitHub Actions + Fastlane



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
<details>
<summary>Click to expand project structure</summary>

```text
├── data
│   ├── api  
│   ├── di
│   ├── mapper              
│   ├── repository                  
├── domain
│   ├── model              
│   ├── repository        
│   └── usecase            
└── presentation
    ├── base         
    ├── navigation
    ├── screen
    └── theme

``` 
</details>


### Data Flow : 
<details>
<summary>Click to expand data follow</summary>
<img width="8100" height="6035" alt="image" src="https://github.com/user-attachments/assets/6d1aac11-645d-433a-8a01-74c9c32da597" />
</details>

