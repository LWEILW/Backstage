<!--<template>-->
<!--  <div>-->
<!--    <canvas id='sky'></canvas>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--  import api from "@/view/xue";-->

<!--  export default {-->
<!--    data() {-->
<!--      return {};-->
<!--    },-->
<!--    // 初始化加载-->
<!--    created() {-->
<!--      this.wavePlay();-->
<!--    },-->
<!--    methods: {-->
<!--      wavePlay() {-->
<!--        // get the canvas and context-->
<!--        var canvas = document.getElementById('sky');-->
<!--        var ctx = canvas.getContext('2d');-->

<!--        // set canvas dims to window height and width-->
<!--        var W = window.innerWidth;-->
<!--        var H = window.innerHeight;-->
<!--        canvas.width = W;-->
<!--        canvas.height = H;-->

<!--        // generate snowflakes and apply attributes-->
<!--        var flakesCount = 100;-->
<!--        var flakes = []; // flake instances-->

<!--        // loop through the empty flakes and apply attributes-->
<!--        for (var i = 0; i < flakesCount; i++) {-->
<!--          flakes.push({-->
<!--            x: Math.random() * W,-->
<!--            y: Math.random() * H,-->
<!--            r: Math.random() * 5 + 2, // 2px - 7px-->
<!--            d: Math.random() + 1-->
<!--          });-->
<!--        }-->

<!--        // draw flakes onto canvas-->
<!--        function drawFlakes() {-->
<!--          ctx.clearRect(0, 0, W, H);-->
<!--          ctx.fillStyle = '#fff';-->
<!--          ctx.beginPath();-->
<!--          for (var i = 0; i < flakesCount; i++) {-->
<!--            var flake = flakes[i];-->
<!--            ctx.moveTo(flake.x, flake.y);-->
<!--            ctx.arc(flake.x, flake.y, flake.r, 0, Math.PI * 2, true);-->
<!--          }-->
<!--          ctx.fill();-->
<!--          moveFlakes();-->
<!--        }-->

<!--        var angle = 0;-->

<!--        function moveFlakes() {-->
<!--          angle += 0.01;-->
<!--          for (var i = 0; i < flakesCount; i++) {-->
<!--            var flake = flakes[i];-->
<!--            flake.y += Math.pow(flake.d, 2) + 1;-->
<!--            flake.x += Math.sin(angle) * 2;-->

<!--            if (flake.y > H) {-->
<!--              flakes[i] = {x: Math.random() * W, y: 0, r: flake.r, d: flake.d};-->
<!--            }-->
<!--          }-->
<!--        }-->

<!--        setInterval(drawFlakes, 25);-->
<!--      }-->

<!--    }-->
<!--  };-->
<!--</script>-->

<!--<style>-->

<!--</style>-->
