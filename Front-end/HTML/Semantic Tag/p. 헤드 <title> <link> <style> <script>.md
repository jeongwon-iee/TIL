# `<title>` html 문서의 대제목

### 검색 최적화 Tips

1. 키워드 단순 나열 X
2. 페이지마다 그에 맞게 title 변경 (무엇에 관한 내용인지)

---

# `<link>` CSS 스타일시트를 첨부

`link:css` + `tab` ⇒ 자동 완성

```html
<link rel="stylesheet" href="style.css">
```

 href attr에 첨부하고 싶은 파일 경로 작성

## 웹에서 폰트 가져오기

### 스포카 한 산스 Spoqa Han Sans

[https://spoqa.github.io/spoqa-han-sans/ko-KR/](https://spoqa.github.io/spoqa-han-sans/ko-KR/)

---

```html
<style> /* CSS 코드 */ </style>
```

HTML 문서 내에 CSS 코드를 작성하고 싶을 때 (굳이?)

따로 만든 css 파일이 소용 없게 된다.

---

# `<script>` javascript 코드

```html
<script src="경로"> </script>
```

```html
<script> //자바스크립트 코드 </script>
```

## `script:src` + `tab` ⇒ 자동완성

왜 script는 head 안에 안 쓰고 body에 사용?

head 안에 script를 사용하면 body를 렌더링 하지 못 하고 기다리게 됨.

`<script>` 태그는 `<body>` 안에서도 **가장 마지막에 사용!**
