<img width="742" alt="스크린샷 2020-09-24 오후 4 39 31" src="https://user-images.githubusercontent.com/45806836/94115324-8775bd80-fe84-11ea-87c6-5f254db8bfaf.png">

# Breadcrumb & Pagination

link를 타고타고 들어가는 것과 Pagination(link들의 종합)

# Breadcrumb

이미지는 눌러지지도 않고 의미 없으니 css에게 넘김! 링크 두 개

---

# Pagingation

Previous, Next 전부 anchor, 개별적인 1, 2, 3, 4, ...

Previous와 Next는 anchor, 1, 2, 3, 4는 리스트로!

**순서가 중요한 리스트** → `<ol>` 과 `<li>` 사용. `<li>`안에 anchor 넣기

...은 그냥 list item으로 해도 되지만, 누를 수 없는 button 같이 보임

submit type이 아니니 **button type & disabled attr** 추가

### WAI-ARIA

인터넷의 접근성을 높여주는 api.

**aria-label** attr: screen reader로 읽었을 때의 label을 지정해줄 수 있음

선택된 Page에서는 Current page라고 마크업 할 수 있음 

## CSS 입히기

현재는 1번 페이지가 선택된 상태이고, previous link가 disabled 된 상태의 style을 적용해야 하니 따로 class로 묶어줌

list item에도 `class` attr 적용할 수 있음!


