import http from "@/api/http";

const attractionStore = {
  namespaced: true,
  state: {
    // map data
    latitude: 37.5012743,
    longitude: 127.039585,
    markerPositions: [],
    planMarkers: [], // 선택 중인 여행 계획 마커
    travelList: [], // 기존 여행 계획 목록
    travelPlanId: 0, // 선택한 기존 여행 계획
    travelPlan: [], // 선택한 기존 여행 상세 경로
    travelMarkers: [], // 선택한 기존 여행 마커

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
          state.markerPositions.push([
            attraction.mapY,
            attraction.mapX,
            attraction.contentId,
            attraction.contentTypeId,
            attraction.title,
          ]);
        });
      }
    },
    SET_PLAN_MARKERS(state, planList) {
      if (planList.length != 0) {
        planList.forEach((planItem) => {
          state.planMarkers.push([
            planItem.mapY,
            planItem.mapX,
            planItem.contentId,
            planItem.contentTypeId,
            planItem.title,
          ]);
        });
      }
    },
    SET_TRAVEL_LIST(state, travelPlanList) {
      state.travelList = [];
      if (travelPlanList.length != 0) {
        travelPlanList.forEach((travelPlan) => {
          let detailList = [];
          travelPlan.planDetailList.forEach((detail) => {
            detailList.push(detail);
          });

          state.travelList.push({
            planId: travelPlan.planId,
            title: travelPlan.title,
            content: travelPlan.content,
            userId: travelPlan.userId,
            planTime: travelPlan.planTime,
            planDetailList: detailList,
          });
        });
      }
    },
    SET_TRAVEL_PLAN(state, plan) {
      state.travelPlanId = plan.planId;
      state.travelPlan = [];
      plan.planDetailList.forEach((detail) => {
        state.travelPlan.push(detail);
      });
    },
    SET_TRAVEL_MARKERS(state) {
      if (state.travelPlan.length != 0) {
        state.travelMarkers = [];
        state.travelPlan.forEach((planItem) => {
          http
            .get(`/attraction/view/${planItem.contentId}`)
            .then(({ data }) => {
              state.travelMarkers.push([
                data.mapY,
                data.mapX,
                data.contentId,
                data.contentTypeId,
                data.title,
                planItem.planOrder,
              ]);
            })
            .catch((error) => {
              console.log(error);
            });
        });
        state.travelMarkers.sort((a, b) => a[5] - b[5]);
        console.log(state.travelMarkers);
      }
    },
    CLEAR_MARKER_POSITIONS(state) {
      state.markerPositions = [];
    },
    CLEAR_PLAN_MARKERS(state) {
      state.planMarkers = [];
    },
    CLEAR_TRAVEL_LIST(state) {
      state.travelList = [];
    },
    CLEAR_TRAVEL_PLAN(state) {
      state.travelPlan = [];
    },
    CLEAR_TRAVEL_MARKERS(state) {
      state.travelMarkers = [];
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
        .get(`/attraction/attractionlist/${attrOpt.doIdx}/${attrOpt.sigunguIdx}/${attrOpt.contentTypeId}`)
        .then(({ data }) => {
          commit("SET_MARKER_POSITIONS", data);
          commit("SET_ATTRACTION_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getSearchAttractionList({ commit }, attrOpt) {
      if (attrOpt.doIdx == null) {
        attrOpt.doIdx = 0;
      }
      if (attrOpt.sigunguIdx == null) {
        attrOpt.sigunguIdx = 0;
      }
      if (attrOpt.word == "") {
        attrOpt.word = "null";
      }
      http
        .get(`/attraction/search/${attrOpt.doIdx}/${attrOpt.sigunguIdx}/${attrOpt.contentTypeId}/${attrOpt.word}`)
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
    getTravelList({ commit }) {
      http
        .get(`/plan/list`)
        .then(({ data }) => {
          if (data.message === "success") {
            commit("SET_TRAVEL_LIST", data.list);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};

export default attractionStore;
