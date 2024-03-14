# 동호회 서비스 앱

## 프로젝트 소개

- 동창들과의 연락과 만남을 만들어주는 앱입니다.
- 앱의 완성 보다는 공부 목적으로 기술들의 구현에 초점을 두었습니다.

## 사용 기술 및 환경

JAVA 17, spring boot 3.2.2, spring data jpa,QueryDsl, mysql, docker, prometheus, grafana, ngrinder, nginx

## TODO

- [ ] OpenAPI spec 적용
- [x] Docker 활용
- [x] 모니터링(APM)
- [x] 부하 테스트 (ngrinder 사용)
- [x] 로드밸런서(nginx)를 사용한 분산 처리, 분산처리 전후 부하테스트 비교
- [ ] MSA 구현
- [ ] 디비 캐싱 (Redis)
- [ ] CI/CD

## 모니터링(APM)

### 환경

prometheusd와 grafana를 사용하여 모니터링 환경을 만들었습니다.

## 분산처리와 성능테스트

docker compose 로 board app의 cpu와 memory를 제한시킨 뒤 세 개로 scale하고, nginx를 로드밸런서로 활용하여 분산처리를 구현했습니다.

### 성능테스트

nGrinder를 사용하여 분산처리 전후로 나누어 부하테스트를 진행 했습니다.

nGrinder를 실행하는 환경과 Spring boot app을 실행하는 환경은 서로 간섭이 일어나지 않게 분리하여 진행했습니다.

결과를 봤을 때 분산처리를 했을 때가 하지 않았을 때보다 TPS가 3배 높아진 모습을 볼 수 있습니다.

분산처리
<img width="1224" alt="Screenshot 2024-03-14 at 4 07 09 PM" src="https://github.com/f-lab-edu/school-reunion/assets/46589339/d8cbfec1-cedd-4f5f-8d89-0c80ffae3a88">

분산처리 x
<img width="1214" alt="Screenshot 2024-03-14 at 4 07 33 PM" src="https://github.com/f-lab-edu/school-reunion/assets/46589339/fd430390-7519-4aaf-93ff-2dbecc457cea">

또한 밑의 이미지를 보면 중간에 Tenured Gen의 메모리 공간이 다 차면서 GC가 실행됨에 따라 STW가 발생해 잠시 응답을 받지 못하는 현상도 볼 수 있었습니다.

<img width="795" alt="Screenshot 2024-03-14 at 4 18 02 PM" src="https://github.com/f-lab-edu/school-reunion/assets/46589339/c7e35197-01ba-47e2-bd1e-190d488ec439">

<img width="474" alt="Screenshot 2024-03-14 at 4 18 13 PM" src="https://github.com/f-lab-edu/school-reunion/assets/46589339/204322d0-9fcf-424a-898b-590769546db8">
