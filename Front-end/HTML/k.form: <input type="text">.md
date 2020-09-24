# form 의 자식요소 input!

---

# 사용자에게 text를 입력 받을 입력창

`<input type="text" />`

## input의 종류, type attr에 맞는 입력창이 생성된다.

`<input type="text" />` 한 줄 가량의 text를 받음

---

## placeholder

## text type의 추가적인 attr "placeholder"

⇒ 아무것도 입력되지 않았을 때 표시할 text

## maxlength & minlength

## text type의 attr "maxlength" "minlength"

⇒ 입력 글자 수를 제한할 때 사용

## required

### text type의 attr "required"

⇒ 필수 입력 창

## disabled

### text type의 attr "disabled"

⇒ 입력창 사용 제한

## value

### text type의 attr "value"

⇒ 입력창에 기본적으로 입력되어 있는 값

---

# 사용자에게 email를 입력 받을 입력창

`<input type="email" />`

input에 @가 있는지 확인해줌

---

# 사용자에게 pw를 입력 받을 입력창

`<input type="password" />`

text를 보이지 않게 처리해줌

minlength와 maxlength와 함께 사용할 수 있다!

---

# 사용자에게 url를 입력 받을 입력창

`<input type="url" />`

---

# 사용자에게 숫자를 입력 받을 입력창

`<input type="number" />`

한글 입력이 안 됨

## number type의 attr "max" "min"

⇒ 숫자의 범위를 제한할 때 사용

---

# 사용자에게 번호를 입력 받을 입력창

### <input type="tel" />

## attr "pattern"

⇒ 정규식으로 나타냄. 정규식은 [https://opentutorials.org/course/909/5143](https://opentutorials.org/course/909/5143) 참고

---

# 사용자에게 파일을 입력 받을 입력창

`<input type="file" />`

파인더 열림

## file type의 attr "accept"

⇒ 파일을 특정 확장자로 제한할 때 사용 **.로 시작 ,로 구분**

첨부할 수 있는 파일이 제한됨

---

input Tag는 **`<form>` 태그의 하위 태그인 것** 잊지 말기!
