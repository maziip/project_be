# BE-001 리뷰 (Reviewer Agent, 2026-09-09)

대상: PR #1 `BE-001: Gradle 애플리케이션 골격과 wrapper 구성` (commit `3502290` + PR 설명 커밋)
기준: 커리큘럼 8.7 코드 리뷰 프롬프트. 코드를 대신 고치지 않고 문제의 이유와 재현 방법을 제시한다.

## 1. 요구사항 충족

- 충족. wrapper(9.7.1) 고정, `./gradlew build`/`test`/`run`이 명령행에서 동작하며 증거가 PR 설명에 있다.

## 2. 버그와 데이터 손실 위험

- 해당 없음 (실행 로직이 샘플 한 줄뿐).

## 3. 트랜잭션·동시성 / 4. 외부 API 실패 처리

- 해당 없음.

## 5. 테스트 누락

- **[Question]** 테스트가 샘플 `appHasAGreeting` 1개뿐이다. 골격 티켓이므로 지금은 허용. 다만 이 테스트의 역할은 "테스트 파이프라인이 동작한다"는 증거이므로, BE-002에서 실제 도메인 테스트가 생기기 전까지는 삭제하지 말 것. BE-001A(CI)에서 이 테스트를 일부러 깨뜨려 CI가 빨간불이 되는지 확인하는 용도로 쓸 수 있다.

## 6. 보안과 민감정보

- 확인 결과 문제 없음. `.env` 미추적, 커밋에 비밀정보 없음, `gradle-wrapper.jar`는 wrapper 동작에 필요한 파일이라 커밋이 맞다.
- **[Minor]** `--scan` 사용으로 빌드 메타데이터가 gradle.com에 1회 업로드됨. PR 설명에 기록되어 있고 재발 방지 결정이 있으므로 추가 조치 없음. 단, 이 결정을 `LEARNING_STATE.md`에도 남길 것.

## 7. 가독성과 책임 분리

- **[Question]** 패키지 이름이 `org.example`이다. `gradle init`의 자리표시자이며, BE-002에서 도메인 클래스를 만들기 전에 실제 이름을 정해야 한다. Java는 패키지와 폴더가 일치해야 하므로 나중에 바꾸면 모든 파일을 이동해야 한다. 지금 정하는 것이 가장 싸다.
- **[Question]** `App.java`/`AppTest.java`(리뷰어 확인 요청 항목). 답: BE-002에서 도메인 모델과 그 테스트가 생기면 그때 삭제한다. 지금 지우면 테스트 0개가 되어 "테스트 통과" 증거가 사라진다.

## 8. 불필요한 복잡성

- **[Minor]** `app/build.gradle.kts`의 `implementation(libs.guava)`는 사용처가 없다. `App.java`는 Guava를 import하지 않는다. 불필요한 의존성은 빌드 시간, 보안 업데이트 부담, 공격 표면을 늘린다.
  - 재현: 해당 줄과 `libs.versions.toml`의 guava 항목을 제거한 뒤 `./gradlew build`가 여전히 성공하면 불필요한 것이 증명된다.
  - 판단은 작성자에게 맡긴다. 제거한다면 이 PR에서, 남긴다면 이유를 PR 설명에 적을 것.

## PR 설명에 대한 의견

- **[Minor]** `## 변경 목적` 항목이 없다. 티켓 문장 한 줄이면 된다.
- **[Minor]** Kotlin DSL 이유에 "(본인이 납득한 것)"이 남아 있다. 세 후보 중 실제로 납득한 하나를 적을 것. 리뷰어가 알고 싶은 것은 선택지가 아니라 작성자의 판단이다.
- **[Question]** "알려진 한계"의 JDK 26 건은 실제로는 이미 완화되어 있다. `build.gradle.kts`의 `toolchain { languageVersion = 21 }`이 **컴파일과 테스트에 쓰는 JDK**를 21로 고정하므로, `JAVA_HOME`이 26을 가리켜도 산출물은 21로 빌드된다. `JAVA_HOME`은 Gradle 자체를 띄우는 JVM만 정한다. 이 차이를 확인해 보고 한계 항목을 수정할 것.
  - 재현: `JAVA_HOME=/opt/homebrew/opt/openjdk ./gradlew build` 실행 후 `javap -v app/build/classes/java/main/org/example/App.class | grep "major version"` 의 값이 `65`(= Java 21)이면 toolchain이 동작한 것이다.

## 사소한 것

- 커밋 메시지 `BE-001:Gradle ...`에서 콜론 뒤 공백 누락. 다음부터 `BE-001: ...` 형식으로 통일.

## 반영 요청 요약

| 심각도 | 항목 | 작성자 결정 |
|---|---|---|
| Minor | 변경 목적 추가, Kotlin DSL 이유 하나로 확정 | |
| Minor | 미사용 Guava 의존성 제거 여부 | |
| Question | 패키지 이름 결정 (BE-002 전) | |
| Question | JDK 26 한계 항목을 toolchain 확인 후 수정 | |
