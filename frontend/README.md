
# 1.  Vuejs 사용
[링크](https://kr.vuejs.org/v2/guide/installation.html)
## 1.1. CDN 으로 Vuejs 사용하기
```html
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
```
## 1.2. Vue Cli 로 Vuejs 사용하기
### 1.2.1. Vue CLI 설치
[링크](https://cli.vuejs.org/guide/)
```
npm install -g @vue/cli @vue/cli-service-global
```bash
### 1.2.2. 프로젝트 생성하기
```bash
vue create vscode-frontend
```
> vue create "프로젝트 이름"
### 1.2.3. package.json에 작성된 모듈 설치
```
npm install
```
### 1.2.4. service
```
vue serve src/App.vue
```
> serve [options] [entry]
   - http://localhost:8080/ 로 접속 가능
### 1.2.5. 빌드
```
vue build src/App.vue
```
> build [options] [entry]
   - dist 디렉토리에 빌드된 파일들이 만들어짐