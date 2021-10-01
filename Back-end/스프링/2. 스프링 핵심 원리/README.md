# 🌱 스프링 핵심 원리

스프링 핵심 원리를 제대로 학습하려면, 객체 지향의 원리와 그 원리를 스프링이 어떻게 지원하는지, 객체 지향과 스프링을 함께 풀어가며 이해해야 한다. 

이 과정을 이해하면 애플리케이션을 개발하고 설계하는 시야가 달라지고, 개발이 정말 재밌어진다.
  
스프링이 제공하는 진정한 핵심 가치는 **객체 지향 프로그래밍**에 있다. 

## 🌱 목차

1. [객체 지향 설계와 스프링](https://github.com/jeongwon-iee/TIL/blob/master/Back-end/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/01.%20%EA%B0%9D%EC%B2%B4%20%EC%A7%80%ED%96%A5%20%EC%84%A4%EA%B3%84%EC%99%80%20%EC%8A%A4%ED%94%84%EB%A7%81.md)  
2. [스프링 핵심 원리 이해1 - 예제 만들기](https://github.com/jeongwon-iee/TIL/blob/master/Back-end/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/02.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC%20%EC%9D%B4%ED%95%B4%20-%20%EC%98%88%EC%A0%9C%20%EB%A7%8C%EB%93%A4%EA%B8%B0.md)
3. [스프링 핵심 원리 이해2 - 객체 지향 원리 적용](https://github.com/jeongwon-iee/TIL/blob/master/Back-end/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/03.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC%20%EC%9D%B4%ED%95%B4%202%20-%20%EA%B0%9D%EC%B2%B4%20%EC%A7%80%ED%96%A5%20%EC%9B%90%EB%A6%AC%20%EC%A0%81%EC%9A%A9.md)
4. [스프링 컨테이너와 스프링 빈](https://github.com/jeongwon-iee/TIL/blob/master/Back-end/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/04.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88%EC%99%80%20%EC%8A%A4%ED%94%84%EB%A7%81%20%EB%B9%88.md)
5. [싱글톤 컨테이너](https://github.com/jeongwon-iee/TIL/blob/master/Back-end/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/05.%20%EC%8B%B1%EA%B8%80%ED%86%A4%20%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88.md)
6. [컴포넌트 스캔](https://github.com/jeongwon-iee/TIL/blob/master/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/06.%20%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8%20%EC%8A%A4%EC%BA%94.md)
7. [의존관계 자동 주입](https://github.com/jeongwon-iee/TIL/blob/master/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/07.%20%EC%9D%98%EC%A1%B4%EA%B4%80%EA%B3%84%20%EC%9E%90%EB%8F%99%20%EC%A3%BC%EC%9E%85.md)
8. [빈 생명주기 콜백](https://github.com/jeongwon-iee/TIL/blob/master/Back-end/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/08.%20%EB%B9%88%20%EC%83%9D%EB%AA%85%EC%A3%BC%EA%B8%B0%20%EC%BD%9C%EB%B0%B1.md)  
9. [빈 스코프](https://github.com/jeongwon-iee/TIL/blob/master/Back-end/%EC%8A%A4%ED%94%84%EB%A7%81/2.%20%EC%8A%A4%ED%94%84%EB%A7%81%20%ED%95%B5%EC%8B%AC%20%EC%9B%90%EB%A6%AC/09.%20%ED%94%84%EB%A1%9C%ED%86%A0%ED%83%80%EC%9E%85%20%EC%8A%A4%EC%BD%94%ED%94%84%20-%20%EC%8B%B1%EA%B8%80%ED%86%A4%20%EB%B9%88%EA%B3%BC%20%ED%95%A8%EA%BB%98%20%EC%82%AC%EC%9A%A9%EC%8B%9C%20%EB%AC%B8%EC%A0%9C%EC%A0%90.md)
10. 다음으로

## 🌱 목표

- 단순히 레퍼런스 문서 X
- 스프링이 왜 만들어졌고, 왜 이런 기능을 제공하는지 스프링 본질에 대한 이해
- 객체 지향 설계를 고민하는 개발자로 성장
