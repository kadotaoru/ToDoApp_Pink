# 俺のToDo
実行動画

https://www.youtube.com/watch?v=UclMypGjUfA

## 🗻Tech
- 開発言語：Kotlin
- DB言語：SQLite

## ❓About app

- 自分のタスクの明確化
- 自分のタスクの進捗状況の見える化「ToDo(作業開始前), Doing(作業中), Done(完了)」

## 🔧機能概要

### 💻WEB
- ユーザー（users）登録・編集
- 各ユーザーは目標を立て、それに対するTODOを期限付きで立てる
- そうすることで、目標と、それを達成するためのTODO（todo_lists）を構造的に定義する
- ユーザーは日々のKPT（daily_kpts）を投稿する
- 他ユーザー情報と目標（goals）とTODO（todo_lists）を閲覧
- Goodアクション、Fightアクション

### 📪API
- ユーザー情報（user）登録・編集・取得
- 目標（.my_goals）の設定・更新
- 日々のKPT（daily_kpts）を投稿する
- Goodアクション、Fightアクション

### 📱クライアント（予定）
- 設定時間になったらKPTを投稿するよう催促
- 目標を達成したユーザーは、全ユーザーからGoodアクションが送られる
- 目標設定に問題があると感じるユーザーに対し（匿名で）コメントを送れる
