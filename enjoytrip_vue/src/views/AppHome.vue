<template>
  <div class="main" data-aos="fade-up" data-aos-duration="200">
    <div class="resume-section-content">
      <h1 class="mb-0">
        여행
        <span class="text-mine">가자&nbsp;</span>
        <!-- <img
          :src="require('@/assets/img/logo_trip.png')"
          alt="logo"
          style="width: 80px"
        /> -->
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="50"
          height="50"
          fill="currentColor"
          class="bi bi-airplane mb-2 text-mine"
          viewBox="0 0 16 16"
        >
          <path
            d="M6.428 1.151C6.708.591 7.213 0 8 0s1.292.592 1.572 1.151C9.861 1.73 10 2.431 10 3v3.691l5.17 2.585a1.5 1.5 0 0 1 .83 1.342V12a.5.5 0 0 1-.582.493l-5.507-.918-.375 2.253 1.318 1.318A.5.5 0 0 1 10.5 16h-5a.5.5 0 0 1-.354-.854l1.319-1.318-.376-2.253-5.507.918A.5.5 0 0 1 0 12v-1.382a1.5 1.5 0 0 1 .83-1.342L6 6.691V3c0-.568.14-1.271.428-1.849Zm.894.448C7.111 2.02 7 2.569 7 3v4a.5.5 0 0 1-.276.447l-5.448 2.724a.5.5 0 0 0-.276.447v.792l5.418-.903a.5.5 0 0 1 .575.41l.5 3a.5.5 0 0 1-.14.437L6.708 15h2.586l-.647-.646a.5.5 0 0 1-.14-.436l.5-3a.5.5 0 0 1 .576-.411L15 11.41v-.792a.5.5 0 0 0-.276-.447L9.276 7.447A.5.5 0 0 1 9 7V3c0-.432-.11-.979-.322-1.401C8.458 1.159 8.213 1 8 1c-.213 0-.458.158-.678.599Z"
          />
        </svg>
      </h1>
      <div class="subheading mb-5">Designed By · Gong YECHAN · Lee WOOJIN</div>
      <p class="lead mb-5">
        얼떨결에 떠나자<br />
        기대는 조금만 하고 눈은 크게 뜨고 짐은 줄이자 어디라도 좋겠지만 사람과
        <br />엉키지 않는 순수한 곳이라면 만사를 팽개치고 뒷일도 접어두자 여정에
        뛰어들어<br />
        보물이 드러나면 꿈꾸던 보자기마다 가득히 채워오자 문물을 얻지 말고
        세상을 담아오자<br />
        태엽을 달아 늘어지게 우려먹자 돌아오면 바로 어디론가 곧 떠날 준비를 하자
        <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;-
        시인·임영준<br />
      </p>
      <div class="social-icons">
        <a class="social-icon" href="#!">
          <b-iconstack font-scale="3">
            <b-icon stacked icon="circle-fill" variant="dark"></b-icon>
            <b-icon
              stacked
              icon="github"
              scale="0.5"
              variant="white"
            ></b-icon> </b-iconstack
        ></a>
        <a class="social-icon" href="#!">
          <b-iconstack font-scale="3">
            <b-icon stacked icon="circle-fill" variant="dark"></b-icon>
            <b-icon
              stacked
              icon="instagram"
              scale="0.5"
              variant="white"
            ></b-icon> </b-iconstack
        ></a>
        <a class="social-icon" href="#!">
          <b-iconstack font-scale="3">
            <b-icon stacked icon="circle-fill" variant="dark"></b-icon>
            <b-icon
              stacked
              icon="facebook"
              scale="0.5"
              variant="white"
            ></b-icon> </b-iconstack
        ></a>
        <a class="social-icon" href="#!">
          <b-iconstack font-scale="3">
            <b-icon stacked icon="circle-fill" variant="dark"></b-icon>
            <b-icon
              stacked
              icon="twitter"
              scale="0.5"
              variant="white"
            ></b-icon> </b-iconstack
        ></a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AppHome",
};
</script>

<style scoped>
.main {
  /* background-color: #fff9f7; */
}

.resume-section-content {
  width: 770px;
  height: 320px;
  margin-left: 50px;
  margin-top: 14vh;
}

h1 {
  font-family: "TTCrownMychewR";
  font-weight: 900;
  font-size: 70px;
  color: #343a40;
}
.text-mine {
  color: #bd5d38;
}
.subheading {
  color: #6c757d;
}
.lead {
  color: #b3b3b3;
  line-height: 40px;
}
.social-icon {
  margin-right: 30px;
}
</style>
