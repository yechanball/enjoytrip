///////////////////////// select box 설정 (지역 선택) /////////////////////////
let date = new Date();
//   var clusterer;
var container = document.getElementById("map");
var options = {
  center: new kakao.maps.LatLng(37.5013068, 127.0396597),
  level: 6,
};
var map = new kakao.maps.Map(container, options);
// 마커 클러스터러를 생성합니다
var clusterer = new kakao.maps.MarkerClusterer({
  map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
  averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
  minLevel: 10, // 클러스터 할 최소 지도 레벨
  disableClickZoom: true, // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
});
// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 마커 클러스터러에 클릭이벤트를 등록합니다
// 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
// 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
kakao.maps.event.addListener(clusterer, "clusterclick", function (cluster) {
  // 현재 지도 레벨에서 1레벨 확대한 레벨
  var level = map.getLevel() - 1;
  // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
  map.setLevel(level, { anchor: cluster.getCenter() });
});

window.onload = function () {
  // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
  let lat, lon; // 위도, 경도
  if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function (position) {
      console.log(
        "GeoLocation을 이용해서 접속 위치 획득!!" +
          position.coords.latitude +
          position.coords.longitude
      );
      lat = position.coords.latitude; // 위도
      lon = position.coords.longitude; // 경도

      map.panTo(new kakao.maps.LatLng(lat, lon));
    });
  } else {
    // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치 설정합니다
    lat = 37.5012743;
    lon = 127.039585;
  }

  // 브라우저가 열리면 시도정보 얻기.
  const url =
    "https://apis.data.go.kr/B551011/KorService/areaCode?serviceKey=YYM82jXrfmJitwNmOarYYsHuAi7uiPdAvGEWfvezD6ufr2e2aILX0Aiu7tctq0ugjZ7cI%2Bfe2BKLDYyE4VYUSA%3D%3D&numOfRows=17&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";
  fetch(url)
    .then((response) => response.json())
    .then((data) => addOption("sido", data));
};

// https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15101578
// let url = "https://apis.data.go.kr/B551011/KorService/areaBasedList?serviceKey=YYM82jXrfmJitwNmOarYYsHuAi7uiPdAvGEWfvezD6ufr2e2aILX0Aiu7tctq0ugjZ7cI%2Bfe2BKLDYyE4VYUSA%3D%3D&numOfRows=/* row 수 */&pageNo=/* 페이지 번호 */&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=C&areaCode=/*지역코드*/&sigunguCode=/*시군구 코드*/";
// let regcode = "*00000000";
// 전국 특별/광역시, 도
// https://apis.data.go.kr/B551011/KorService/areaCode?serviceKey=YYM82jXrfmJitwNmOarYYsHuAi7uiPdAvGEWfvezD6ufr2e2aILX0Aiu7tctq0ugjZ7cI%2Bfe2BKLDYyE4VYUSA%3D%3D&numOfRows=17&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json

// 시도가 바뀌면 구군정보 얻기.
document.querySelector("#sido").addEventListener("change", function () {
  if (this[this.selectedIndex].value) {
    let regcode = this[this.selectedIndex].value;
    const url =
      "https://apis.data.go.kr/B551011/KorService/areaCode?serviceKey=YYM82jXrfmJitwNmOarYYsHuAi7uiPdAvGEWfvezD6ufr2e2aILX0Aiu7tctq0ugjZ7cI%2Bfe2BKLDYyE4VYUSA%3D%3D&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=" +
      regcode +
      "&_type=json";
    fetch(url)
      .then((response) => response.json())
      .then((data) => addOption("gugun", data));
  } else {
    initOption("gugun");
  }
});

function addOption(selid, data) {
  let opt = ``;
  initOption(selid);
  switch (selid) {
    case "sido":
      opt += `<option value="">시도선택</option>`;
      data.response.body.items.item.forEach(function (regcode) {
        opt += `
        <option value="${regcode.code}">${regcode.name}</option>
        `;
      });
      break;
    case "gugun":
      opt += `<option value="">구군선택</option>`;
      data.response.body.items.item.forEach(function (regcode) {
        opt += `
        <option value="${regcode.code}">${regcode.name}</option>
        `;
      });
      break;
  }
  document.querySelector(`#${selid}`).innerHTML = opt;
}

function initOption(selid) {
  let options = document.querySelector(`#${selid}`);
  options.length = 0;
  if (selid == "gugun")
    options.innerHTML = `<option value="">구군선택</option>`;
}

///////////////////////// 지역별 관광 정보 /////////////////////////
document.querySelector("#list-btn").addEventListener("click", function () {
  let url = "https://apis.data.go.kr/B551011/KorService/areaBasedList";
  let sidoSel = document.querySelector("#sido");
  let sidoCode = sidoSel[sidoSel.selectedIndex].value;
  let gugunSel = document.querySelector("#gugun");
  let gugunCode = gugunSel[gugunSel.selectedIndex].value;
  let tourType = document.querySelector("#tour-type").value;
  let queryParams =
    encodeURIComponent("serviceKey") +
    "=" +
    "YYM82jXrfmJitwNmOarYYsHuAi7uiPdAvGEWfvezD6ufr2e2aILX0Aiu7tctq0ugjZ7cI%2Bfe2BKLDYyE4VYUSA%3D%3D"; /*Service Key*/
  queryParams +=
    "&" +
    encodeURIComponent("numOfRows") +
    "=" +
    encodeURIComponent("100"); /*페이지당건수*/
  queryParams +=
    "&" +
    encodeURIComponent("pageNo") +
    "=" +
    encodeURIComponent("1"); /*페이지번호*/
  queryParams +=
    "&" +
    encodeURIComponent("MobileOS") +
    "=" +
    encodeURIComponent("ETC") /*플랫폼 정보*/ +
    "&" +
    encodeURIComponent("MobileApp") +
    "=" +
    encodeURIComponent("AppTest") /*서비스명*/ +
    "&" +
    encodeURIComponent("_type") +
    "=" +
    encodeURIComponent("json") +
    "&" +
    encodeURIComponent("listYN") +
    "=" +
    encodeURIComponent("Y") +
    "&" +
    encodeURIComponent("arrange") +
    "=" +
    encodeURIComponent("C");
  queryParams +=
    "&" +
    encodeURIComponent("contentTypeId") +
    "=" +
    encodeURIComponent(tourType); /*관광타입*/
  queryParams +=
    "&" +
    encodeURIComponent("areaCode") +
    "=" +
    encodeURIComponent(sidoCode); /*시도*/
  queryParams +=
    "&" +
    encodeURIComponent("sigunguCode") +
    "=" +
    encodeURIComponent(gugunCode); /*구군*/

  fetch(`${url}?${queryParams}`)
    .then((response) => response.json())
    .then((data) => makeList(data));
});

var radioboxList = []; // place-pick
function makeList(data) {
  var markers = [];
  radioboxList = [];
  var overlayDivs = document.querySelectorAll(".customoverlay");
  for (let oDiv of overlayDivs) {
    // 마커 초기화
    oDiv.style.display = "none";
  }
  clusterer.clear();
  let currPosition = false;
  data.response.body.items.item.forEach((place) => {
    if (!currPosition && place.mapx && place.mapy) {
      map.panTo(new kakao.maps.LatLng(place.mapy, place.mapx));
      console.log("start position " + place.mapy + ", " + place.mapx);
      currPosition = true;
    }
  });
  if (!currPosition) {
    map.panTo(new kakao.maps.LatLng(37.5013068, 127.0396597));
  }

  document.querySelector("table").setAttribute("style", "display: ;");
  let tbody = document.querySelector("#tourList");
  initTable();
  var num = 0;
  data.response.body.items.item.forEach((place) => {
    num++;
    let tr = document.createElement("tr");
    tr.style.height = "100px";
    tr.style.overflow = "hidden";

    let imageTd = document.createElement("td");
    let imgLabel = document.createElement("label");
    imgLabel.setAttribute("for", "place-pick-" + num);
    let imgEl = document.createElement("img");
    imgEl.src = place.firstimage;
    imgEl.style.width = "100px";
    imgLabel.appendChild(imgEl);
    imageTd.appendChild(imgLabel);
    tr.appendChild(imageTd);

    let nameTd = document.createElement("td");
    let nameLabel = document.createElement("label");
    nameLabel.setAttribute("for", "place-pick-" + num);
    nameLabel.appendChild(document.createTextNode(place.title));
    nameTd.appendChild(nameLabel);
    tr.appendChild(nameTd);

    let radioTd = document.createElement("td");
    radioTd.innerHTML = `<input type="radio" name="place" id="place-pick-${num}" value="${place.mapy},${place.mapx}"/>`;
    tr.appendChild(radioTd);

    if (place.firstimage) {
      var imageSrc = place.firstimage, // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
    } else if (place.firstimage2) {
      imgEl.src = place.firstimage2;
      var imageSrc = place.firstimage2, // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
    } else {
      imgEl.src = "./assets/img/noimage.png";
      var imageSrc = "./assets/img/noimage.png", // 기본 마커이미지입니다
        imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
    }

    if (place.mapy && place.mapx) {
      // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imageOption
        ),
        markerPosition = new kakao.maps.LatLng(place.mapy, place.mapx); // 마커가 표시될 위치입니다

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        position: markerPosition,
        image: markerImage, // 마커이미지 설정
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      markers.push(marker);
      // marker.setMap(map);
      clusterer.addMarker(marker);

      // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      var content = `<div class="customoverlay">
          <a href="https://map.kakao.com/link/map/${place.title},${place.mapy},${place.mapx}" target="_blank">
            <span class="title">${place.title}</span>
          </a>
        </div>`;

      // 커스텀 오버레이가 표시될 위치입니다
      var position = new kakao.maps.LatLng(place.mapy, place.mapx);

      // 커스텀 오버레이를 생성합니다
      var customOverlay = new kakao.maps.CustomOverlay({
        map: map,
        position: position,
        content: content,
        yAnchor: 1,
      });
    }

    tbody.appendChild(tr);
  });
  radioboxList = document.querySelectorAll("input[type=radio]");

  for (let marker of markers) {
    // 마커 표시
    marker.setMap(map);
  }
}

// 선택한 위치로 이동
document.addEventListener("input", function () {
  for (var radiobox of radioboxList) {
    radiobox.addEventListener("change", function (event) {
      if (event.target.checked) {
        checkLocation = event.target.value.split(",");
        console.log("현재 선택한 위치:" + checkLocation);
        map.panTo(new kakao.maps.LatLng(checkLocation[0], checkLocation[1]));
      }
    });
  }
});

function initTable() {
  let tbody = document.querySelector("#tourList");
  let len = tbody.rows.length;
  for (let i = len - 1; i >= 0; i--) {
    tbody.deleteRow(i);
  }
}
