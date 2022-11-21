import http from "@/api/http";

const attractionStore = {
  namespaced: true,
  state: {
    // map data
    latitude: 37.5012743,
    longitude: 127.039585,
    markerPositions: [],

    // attraction data
    dos: [{ value: null, text: "도 선택" }],
    sigungus: [{ value: null, text: "시/군/구 선택" }],
    tourtypes: [
      { value: 0, text: "전체" },
      { value: 12, text: "관광지" },
      { value: 14, text: "문화시설" },
      { value: 15, text: "행사/공연/축제" },
      { value: 25, text: "여행코스" },
      { value: 28, text: "레포츠" },
      { value: 32, text: "숙박" },
      { value: 38, text: "쇼핑" },
      { value: 39, text: "음식점" },
    ],
    attractions: [],
    attraction: null,
  },
  getters: {},
  mutations: {
    // map data set
    SET_LATITUDE(state, latitude) {
      state.latitude = latitude;
    },
    SET_LONGITUDE(state, longitude) {
      state.longitude = longitude;
    },
    SET_MARKER_POSITIONS(state, attractionList) {
      if (attractionList.length != 0) {
        attractionList.forEach((attraction) => {
          if (attraction.mapY != 0 && attraction.mapX != 0) {
            // 위치가 0인 장소 제외하고 목록 생성
            state.markerPositions.push([
              attraction.mapY,
              attraction.mapX,
              attraction.contentId,
              attraction.contentTypeId,
              attraction.title,
            ]);
          }
        });
      }
    },
    CLEAR_MARKER_POSITIONS(state) {
      state.markerPositions = [];
    },

    // attraction data set
    SET_DO_LIST(state, dos) {
      dos.forEach((doItem) => {
        state.dos.push({ value: doItem.doIdx, text: doItem.doName });
      });
    },
    SET_SIGUNGU_LIST(state, sigungus) {
      sigungus.forEach((sigunguItem) => {
        state.sigungus.push({
          value: sigunguItem.sigunguIdx,
          text: sigunguItem.sigunguName,
        });
      });
    },
    SET_ATTRACTION_LIST(state, attractionList) {
      console.log("검색된 관광지 개수 : " + attractionList.length);
      if (attractionList.length != 0) {
        attractionList.forEach((atr) => {
          state.attractions.push({
            addr1: atr.addr1,
            addr2: atr.addr2,
            areaCode: atr.areaCode,
            bookTour: atr.bookTour,
            cat1: atr.cat1,
            cat2: atr.cat2,
            cat3: atr.cat3,
            contentId: atr.contentId,
            contentTypeId: atr.contentTypeId,
            createdTime: atr.createdTime,
            firstImage: atr.firstImage,
            firstImage2: atr.firstImage2,
            mapX: atr.mapX,
            mapY: atr.mapY,
            mLevel: atr.mLevel,
            modifiedTime: atr.modifiedTime,
            readCount: atr.readCount,
            sigunguCode: atr.sigunguCode,
            tel: atr.tel,
            title: atr.title,
            zipCode: atr.zipCode,
            overview: atr.overview,
          });
        });
      }
    },
    SET_ATTRACTION(state, atr) {
      state.attraction = {
        addr1: atr.addr1,
        addr2: atr.addr2,
        areaCode: atr.areaCode,
        bookTour: atr.bookTour,
        cat1: atr.cat1,
        cat2: atr.cat2,
        cat3: atr.cat3,
        contentId: atr.contentId,
        contentTypeId: atr.contentTypeId,
        createdTime: atr.createdTime,
        firstImage: atr.firstImage,
        firstImage2: atr.firstImage2,
        mapX: atr.mapX,
        mapY: atr.mapY,
        mLevel: atr.mLevel,
        modifiedTime: atr.modifiedTime,
        readCount: atr.readCount,
        sigunguCode: atr.sigunguCode,
        tel: atr.tel,
        title: atr.title,
        zipCode: atr.zipCode,
        overview: atr.overview,
      };
    },
    CLEAR_DO_LIST(state) {
      state.dos = [{ value: null, text: "도 선택" }];
    },
    CLEAR_SIGUNGU_LIST(state) {
      state.sigungus = [{ value: null, text: "시/군/구 선택" }];
    },
    CLEAR_ATTRACTION_LIST(state) {
      state.attractions = [];
    },
    CLEAR_ATTRACTION(state) {
      state.attraction = null;
    },
  },
  actions: {
    // map data get
    getPosition({ commit }, position) {
      commit("SET_LATITUDE", position.latitude);
      commit("SET_LONGITUDE", position.longitude);
    },

    // attraction data get
    getDo({ commit }) {
      http
        .get(`/attraction/dolist`)
        .then(({ data }) => {
          commit("SET_DO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getSigungu({ commit }, doIdx) {
      http
        .get(`/attraction/sigungulist/${doIdx}`)
        .then(({ data }) => {
          commit("SET_SIGUNGU_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getAttractionList({ commit }, attrOpt) {
      http
        .get(
          `/attraction/attractionlist/${attrOpt.doIdx}/${attrOpt.sigunguIdx}/${attrOpt.contentTypeId}`
        )
        .then(({ data }) => {
          commit("SET_MARKER_POSITIONS", data);
          commit("SET_ATTRACTION_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getAttraction({ commit }, contentId) {
      http
        .get(`/attraction/view/${contentId}`)
        .then(({ data }) => {
          commit("SET_ATTRACTION", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};

export default attractionStore;
