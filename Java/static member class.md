## static class

inner class가 아닌 경우 static class로 만들 수 없다. static inner class는 outer class의 static member인 nested class이다. 

outer class의 인스턴스화 없이 접근 가능하고, 다른 static member들ㅇ르 사용 가능하다.

static member와 같게, static nested class는 outer class의 인스턴스 변수와 메소드에 접근할 수 없다.

### **Syntax**

```java
class MyOuter {
   static class Nested_Demo {
   }
}
```

---

## Inner(내부) class와 static member class(내부 정적 클래스)의 차이

### 개요

```java
public class MyClass {
    class InnerClass{}

    static class StaticMemberClass{} //내부 클래스에 static이 붙는다면?
}
```

클래스 내부에 선언된 두개의 내부 클래스에 대한 차이점에 대해서 얘기해보겠습니다. 만약에 'static'키워드가 붙은 내부 클래스를 보면서 **~~'아 static이니까 메모리에 하나만 올라가는 인스턴스 인가보다'~~ 라고 생각하신다면..** 큰 오해를 하고 계신다는 점을 미리 알려드립니다. :)

원래 'inner 혹은 내부'라는 키워드는 static class에 사용하지 않습니다. 이 포스팅에서는 차이점을 비교하기 위해서 편의상 사용하겠습니다. 정확한 명칭은 static member class (혹은 정적 멤버 클래스) 라고 해야합니다.

### 1. 내부 클래스로 인스턴스를 두번 생성한다면?

```java
MyClass.InnerClass instance1 = new MyClass().new InnerClass();
MyClass.InnerClass instance2 = new MyClass().new InnerClass();

if(!instance1.equals(instance2)) System.out.println("다른 객체");
```

InnerClass의 새로운 인스턴스를 만들게 될 때 문법은 위와 같이 new 연산자를 두 번 사용합니다. **외부 클래스에 대한 인스턴스를 이용해서 내부 클래스의 인스턴스를 생성합니다.**

내부 클래스로 객체를 두번 생성한다면, 두개 객체는 같은 참조일까요? 다른 참조일까요?

코드의 출력결과는 '**다른 참조**'가 맞습니다. 내부 클래스라고 하더라도 결국 클래스이기 때문에 다른 인스턴스를 만들게 됩니다. (여기까진 어렵지 않죠)

### 2. static 멤버 클래스로 두번 만든다면?

```java
MyClass.StaticMemberClass staticMember1 = new MyClass.StaticMemberClass();
MyClass.StaticMemberClass staticMember2 = new MyClass.StaticMemberClass();

if(!staticMember1.equals(staticMember2)) System.out.println("다른 객체");
```

StaticMemberClass는 new 연산자를 한번만 사용해도 새로운 인스턴스를 만들 수 있습니다. **외부 클래스에 대한 인스턴스가 굳이 필요 없습니다.**

static 클래스로 객체를 2번 생성한다면, 두개의 객체는 같은 참조일까요? 다른 참조일까요?

정답은 '**다른 참조**' 입니다. 혹시 '같은 참조'로 예상하신 분들은 'static'이라는 키워드에 현혹(?)되었기 때문입니다.

자바 (혹은 객체지향)을 처음 배우던 꼬꼬마 시절을 떠올려 봅시다.

클래스는 '설계도', 인스턴스는 '설계도로 만든 실체'라고 많이 배우죠.

클래스의 역할은 인스턴스를 만드는 '설계도'의 역할을 할 뿐이지, 그 자체가 인스턴스처럼 존재할 수 없습니다.  static이라는 키워드가 클래스에 붙게 된다면 인스턴스를 생성하는 방식이 달라지는 것이지 클래스가 갑자기 인스턴스의 역할을 하지 못한다는 겁니다.

좀 더 얘기 해보자면, 우리가 만드는 모든 클래스들은 원래 static 메모리에 올라가는 'static'입니다. 내부 클래스에 static 키워드를 붙이면, 외부 인스턴스 없이 내부 클래스의 인스턴스를 바로 생성할 수 있다는 차이점이 존재 할 뿐 기능적 차이는 없습니다.

### 진짜 기능적 차이가 없나?

인스턴스 선언문 외에도 내부적으로 약간 다른 점이 있습니다. 

InnerClass는 MyClass의 인스턴스가 존재해야지 만들어 질 수 있습니다. 그렇다면 InnerClass는 자신을 만들어준 인스턴스에 대한 '외부 참조'를 갖게 됩니다. 그리고 이 참조는 숨겨져 있어서 '숨은 외부 참조' 라고 불립니다.

반면에 StaticMemberClass는 외부 인스턴스 없이도 만들어 질 수 있기 때문에 '외부 참조'가 존재하지 않습니다.

```java
// "myClass 대한 숨은 외부 참조"를 갖는다.
MyClass myClass = new MyClass();
MyClass.InnerClass innerClass = myClass.new InnerClass();

// 외부 참조 (MyClass 인스턴스) 없이도 만들어질 수 있다.
MyClass.StaticMemberClass staticMemberClass = new MyClass.StaticMemberClass();
```

'외부 참조'가 존재하기 때문에 내부 클래스는 외부 클래스에게 접근이 가능하다는 점이 있습니다. 

```java
public class MyClass {
    void myMethod(){}
    class InnerClass{
        void innerClassMethod() {
            MyClass.this.myMethod();
        }
    }

    static class StaticMemberClass{
        void staticMemberClassMethod() {
            MyClass.this.myMethod(); // 컴파일 에러 (static → instance 참조 X)
        }
    }
}
```

'외부 참조'는 2가지 단점은 다음과 같습니다.

1. 참조값을 담아야 하기 때문에, 인스턴스 생성시 시간적, 공간적으로 성능이 낮아진다.
2. **외부 인스턴스에 대한 참조가 존재하기 때문에, 가비지 컬렉션이 인스턴스 수거를 하지 못하여 메모리 누수가 생길 수 있다.**
