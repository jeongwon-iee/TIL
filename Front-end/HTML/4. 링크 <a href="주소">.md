# 링크 Anchor

현재 보고 있는 페이지에서 다른 페이지로 이동할 때, 한 페이지 내에서 다른 위치로 이동할 때도 link 사용

### 반드시 명시해야 할 attribute가 있다! href

태그의 속성은 태그에 부가적인 정보를 줌.

### href = hypertext(html 문서) reference(주소값)

⇒ 어디로 이동할지 알려주는 attribute

```html
 <a href="주소"> 링크 </a>
```

Anchor Tag 사용 시 어디로 이동할지 href attr를 사용해야 한다.

---

## href 주소값 표기 방법

(1) 웹 URL

```html
<a href="[https://edu.goorm.io/](https://edu.goorm.io/)">
```

(2) 페이지 내 이동: 이동하고자 하는 섹션의 id 값

```html
<a href="#hello">
```

(3) 메일 쓰기

```html
<a href="mailto:메일주소"> 
```

해당 주소로 메일 쓰기로 바로 연결됨

(4) 전화 걸기

```html
<a href="tel:전화번호">
```

---

## 추가 attribute target="_blank"

새 탭으로 페이지를 열고 싶을 때!

```target="_blank"``` 사용 시 새 탭을 띄워 링크로 이동하게 된다.

---

새 탭에 열렸으면 좋겠음 → ```target="_blank"```

