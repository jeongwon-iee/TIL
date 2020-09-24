# 체크박스 입력창 radio

input type="radio" 쓸 때, 반드시 **name**과 **value** attr를 적어줘야 함.

```html
<input type="radio" />
```

이름을 부여할 땐 마찬가지로 id를 부여한 후 `<label>` 태그 사용!

둘 중 하나만 선택 되어야 함!

radio 입장에선 밑에 애가 같은 그룹이란 걸 알길이 없음.

## radio type의 `attr name=""`

⇒ radio들에게 같은 이름 name 을 붙여 그룹이 되게 해줌

하나만 선택 됨.

form에 넣고 button 만들기

뭘 골라도 url의 subscription이 "on"임 → 구별 되게 value값을 줘야 함

## radio type의 attr `value=""`

name-value 쌍으로 서버에 전달됨.

미구독 체크 → submit

subscription(name)값이 unsubscribed(value)가 된 것을 확인할 수 있음

**name은 그룹핑 목적! value는 값 구별 목적!**

다른 radio group을 만들고 싶으면 다른 name의 radio group을 만들면 됨

---

# 다중선택 체크박스 입력창 checkbox

```html
<input type="checkbox" />
```

다중 선택이 가능해짐!

이 경우에도 다음 체크박스들이 **한 그룹임을 알려줘야 하기 때문에**

name attr를 줘야 함!

## checkbox type의 attr `name=""`

⇒ checkbox들에게 같은 이름 name 을 붙여 그룹이 되게 해줌

뭘 골라도 language가 "on"임 → 구별 되게 value값을 줘야 함

## checkbox type의 attr `value=""`

name-value 쌍으로 서버에 전달됨.


값들이 name-value쌍으로 전달됨.
