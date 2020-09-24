# 이미지 
```html
<img src="" alt="">
```

---

# 오디오 
```html
<audio src="파일경로">
```

```html
<audio src=""> <audio>
```

### 프로젝트에 assets 폴더 만든 후 오디오 파일 넣기

---

### audio type attr controls

⇒ 재생 버튼 등을 표시 해줌

### audio type attr autoplay

⇒ 컨트롤 버튼 없이 자동 재생시키고 싶을 때

### audio type attr loop

⇒ 무한 재생 시키고 싶을 때

---

```html
<source src="경로" type="audio/" />
```

⇒ 경우에 따라 여러 가지 오디오 파일을 넣고 싶을 때

---

# 비디오 
```html
<video src="파일경로">
```

```html
<video src=""> <video>
```

### 프로젝트에 assets 폴더 만든 후 비디오 파일 넣기

### video type attr `controls`

⇒ 재생 버튼 등을 표시 해줌

### video type attr `autoplay`

⇒ 컨트롤 버튼 없이 자동 재생시키고 싶을 때

### video type attr `loop`

⇒ 무한 재생 시키고 싶을 때

---

```html
<source src="경로" type="video/" />
```

타입은 html mp4 source type이라고 검색하면 됨

---

# iframe 또다른 html 문서나 컨텐츠 embed 하기


유튜브 영상에서 `퍼가기(embed)` 클릭

`<iframe>` 코드를 html 파일에 복사 붙여넣기

 유튜브 영상이 embed 되었음!
