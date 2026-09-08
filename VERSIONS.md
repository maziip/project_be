# Versions

| 기술 | 고정 버전 | 공식 지원 문서 | 선택 이유 | 확인일 |
|---|---|---|---|---|
| Java | 21 (Temurin 21.0.12.1+1, LTS) | https://adoptium.net/support/ (지원 종료: 2029-12 이상) | LTS. opensearch-java COMPATIBILITY.md가 JDK 8/11/17/21을 명시하고 25는 미기재 → 호환 위험 최소화 | 2026-09-08 |
| Spring Boot | 4.1.1 | https://docs.spring.io/spring-boot/system-requirements.html (Java 17~26, Gradle 8.14+ 또는 9.x, Spring Framework 7.0.9+) | 현재 GA. OSS 지원 정책은 릴리스 후 13개월(https://enterprise.spring.io/lts-releases) | 2026-09-08 |
| Gradle | 9.7.1 (wrapper) | https://gradle.org/releases/ (2026-08-19 릴리스) | Spring Boot 4.1.1이 9.x 전체 지원 | 2026-09-08 |
| MySQL | 9.7 LTS (Docker `mysql:9.7`) | https://www.mysql.com/support/supportedplatforms/database.html, https://hub.docker.com/_/mysql (`lts` 태그 = 9.7.2) | 현재 LTS. 8.4도 LTS이나 최신 LTS 선택(학습자 결정) | 2026-09-08 |
| OpenSearch | 3.7.0 | https://opensearch.org/releases/ (2026-06-09, 3.x = Current, 2.x = Maintenance) | 공식 지원 중인 최신 안정 버전 | 2026-09-08 |
| opensearch-java client | 3.x (문서 예시 3.9.0) | https://docs.opensearch.org/latest/clients/java/, https://github.com/opensearch-project/opensearch-java/blob/main/COMPATIBILITY.md | 3.x 클라이언트는 서버 1.x~3.x 호환 | 2026-09-08 |
| Docker / Compose | Docker Desktop 4.90.0 (Engine 29.7.2, Compose v5.5.1) | https://docs.docker.com/desktop/setup/install/mac-install/ | 로컬 MySQL·OpenSearch 실행 | 2026-09-08 |

## 미확인 항목

- OpenSearch 3.7.0 공식 Docker 이미지에 k-NN(벡터 검색) 플러그인 포함 여부: 컨테이너 기동 후 `GET _cat/plugins`로 확인해 기록한다.
- Spring Boot 4.1.x의 정확한 OSS 지원 종료일: 릴리스 노트에서 릴리스일을 확인해 13개월 규칙으로 계산한다.
- 위 버전 조합의 실제 빌드 확인은 BE-001에서 수행한다.

버전을 변경할 때는 변경 이유, migration 영향, 테스트 결과를 기록한다.
