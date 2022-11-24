<template>
  <div id="attraction-map">
    <div id="map"></div>
    <b-modal v-model="modalShow" size="xl" ok-only>
      <attraction-detail></attraction-detail>
    </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import AttractionDetail from "@/components/attraction/AttractionDetail";

export default {
  name: "AttractionMap",
  components: { AttractionDetail },
  data() {
    return {
      map: null,
      lat: null,
      lon: null,
      markers: [],
      modalShow: false,
      nowOverlay: null,
    };
  },
  computed: {
    ...mapState("attractionStore", [
      "latitude",
      "longitude",
      "markerPositions",
    ]),
  },
  watch: {
    // 마커 위치 변화에 따른 지도 마커 표시
    markerPositions: function (markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }
      const positions = markerPositions.map(
        (position) => new kakao.maps.LatLng(position[0], position[1])
      );
      if (markerPositions.length > 0) {
        this.markers = markerPositions.map((position) => {
          var pos = new kakao.maps.LatLng(position[0], position[1]);

          // 커스텀 오버레이
          var content = `<div class ="label" style="padding: 5px; background-color: rgba(255, 255, 255, 0.75);"><span class="left"></span><span class="center">${position[4]}</span><span class="right"></span></div>`;
          var customOverlay = new kakao.maps.CustomOverlay({
            position: pos,
            content: content,
          });

          // 인포 윈도우
          // const infoWindow = new kakao.maps.InfoWindow({
          //   removable: false,
          //   content: `<div style="padding:0px; background-color: rgba(255, 255, 255, 0.75);">
          //               <p>${position[4]}</p>
          //             </div>`,
          // });

          var imageSrc; // 마커 이미지
          var imageSize = new kakao.maps.Size(40, 40); // 기본 마커 이미지의 크기
          if (position[3] == 12) {
            imageSrc = require("@/assets/img/icon/city.png"); // 관광지
          } else if (position[3] == 14) {
            imageSrc = require("@/assets/img/icon/cathedral.png"); // 문화시설
          } else if (position[3] == 15) {
            imageSrc = require("@/assets/img/icon/guitar.png"); // 행사/공연/축제
          } else if (position[3] == 25) {
            imageSrc = require("@/assets/img/icon/map.png"); // 여행코스
          } else if (position[3] == 28) {
            imageSrc = require("@/assets/img/icon/boat.png"); // 레포츠
          } else if (position[3] == 32) {
            imageSrc = require("@/assets/img/icon/bed.png"); // 숙박
          } else if (position[3] == 38) {
            imageSrc = require("@/assets/img/icon/shopping.png"); // 쇼핑
          } else if (position[3] == 39) {
            imageSrc = require("@/assets/img/icon/restaurant.png"); // 음식점
          } else {
            imageSrc = require("@/assets/img/icon/marker.png"); // 기본 마커 이미지
            imageSize = new kakao.maps.Size(20, 30); // 기본 마커 이미지의 크기
          }

          var imageOption = { offset: new kakao.maps.Point(20, 20) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

          var markerImage = new kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imageOption
          );

          var marker = new kakao.maps.Marker({
            map: this.map,
            position: pos,
            clickable: true, // 마커 클릭 가능
            image: markerImage,
          });

          // 마커 mouseover 이벤트
          kakao.maps.event.addListener(marker, "mouseover", () => {
            // infoWindow.open(this.map, marker);
            customOverlay.setMap(this.map);
          });
          kakao.maps.event.addListener(marker, "mouseout", () => {
            // infoWindow.close(this.map, marker);
            customOverlay.setMap(null);
          });

          // // 마커 클릭 이벤트
          kakao.maps.event.addListener(marker, "click", () => {
            // 마커 클릭시 위치 상세 정보 팝업 띄우기
            this.getAttraction(position[2]);
            this.map.panTo(new kakao.maps.LatLng(position[0], position[1]));
            this.modalShow = !this.modalShow;
          });

          return marker;
        });
        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );
        this.map.setBounds(bounds);
      }
    },
    // 모달 감지
    modalShow: function () {
      // 모달이 닫혀 있는 경우 관광지 상세 정보 삭제
      if (!this.modalShow) {
        this.CLEAR_ATTRACTION();
      }
    },
  },
  methods: {
    ...mapMutations("attractionStore", ["CLEAR_ATTRACTION"]),
    ...mapActions("attractionStore", ["getPosition", "getAttraction"]),
    initMap() {
      // 현재 위치를 중심으로 지도 생성
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.5012743, 127.039585),
        level: 6,
      };
      this.map = new kakao.maps.Map(container, options);

      // geolocation을 사용할 수 있는지 확인
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position) => {
          // GeoLocation을 이용해서 접속 위치 획득
          this.lat = position.coords.latitude; // 위도
          this.lon = position.coords.longitude; // 경도
          this.getPosition({
            latitude: this.lat,
            longitude: this.lon,
          });
          // console.log("GeoLocation 접속위치 획득=>위도:" + this.lat + ", 경도:" + this.lon);
          this.map.panTo(new kakao.maps.LatLng(this.lat, this.lon));
        });
      }
    },
  },
  created() {
    this.modalShow = false;
  },
  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_MAP_KEY}`;
      document.head.appendChild(script);
    } else {
      this.initMap();
    }
  },
};
</script>

<style>
#attraction-map {
  height: 90%;
  /* TODO: 배경색 수정하기 */
  /* background-color: rgb(167, 235, 199); */
}

#map {
  /* TODO: 지도 크기 수정하기 */
  height: 100%;
}
</style>
