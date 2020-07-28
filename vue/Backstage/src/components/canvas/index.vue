<template>
  <div id="wave" :style="`height:${waveAllHeight}px`">
    <div class="wave-box">
      <canvas id="wave1"></canvas>
      <canvas id="wave2"></canvas>
      <canvas id="wave3"></canvas>
      <canvas id="wave4"></canvas>
      <canvas id="sky"></canvas>
    </div>
  </div>
</template>

<script>
  export default {
    name: "wavePlay",
    data() {
      return {
        //波浪的整体高度
        waveAllHeight: 0,
        //波浪个数
        waveCount: 4,
        //波浪起伏高度
        waveHeight: 20,
      }
    },
    created() {
      this.waveAllHeight = window.innerHeight/2
    },
    mounted() {
      this.wavePlay("wave1", 100, "hsl(230, 90%, 61%)", 3000);
      this.wavePlay("wave2", 130, "hsl(200, 70%, 61%)", 3000);
      this.wavePlay("wave3", 160, "hsl(180, 50%, 61%)", 4000);
      this.wavePlay("wave4", 200, "hsl(190, 30%, 61%)", 4000);
      this.wavePlayXue();
    },
    methods: {
      //  * 底部波浪
      //  * @param $canvasID     canvasID
      //  * @param $progress     波浪位置的高度
      //  * @param $maveColor    波浪颜色
      //  * @param $time         运动周期
      //
      //波浪运动动画
      wavePlay($canvasID, $progress, $maveColor, $time) {
        let _this = this;
        let waveWidth = 3800, //波浪长度
          offset = 0,
          waveHeight = _this.waveHeight, //波浪起伏高度
          waveCount = _this.waveCount, //波浪个数
          startX = -1000,
          startY = 212, //canvas 高度
          progress = $progress, //波浪位置高度
          d2 = waveWidth / waveCount, //单个波浪的宽度
          d = d2 / 2,
          hd = d / 2,
          c = document.getElementById($canvasID),
          ctx = c.getContext("2d");
        c.width = 2555; //画布宽度
        c.height = _this.waveAllHeight; //画布高度
        function move() {
          offset -= 5;
          if (-1 * offset === d2) {
            offset = 0;
          }
          ctx.clearRect(0, 0, c.width, c.height);
          ctx.fillStyle = $maveColor; //画布填充色
          ctx.beginPath();
          let offsetY = startY - progress;
          //绘制贝塞尔曲线
          ctx.moveTo(startX - offset, offsetY); //开始点
          for (let i = 0; i < waveCount; i++) {
            let dx = i * d2;
            let offsetX = dx + startX - offset;
            //   quadraticCurveTo(1,1,2,2) 方法通过使用表示二次贝塞尔曲线的指定控制点，向当前路径添加一个点。(1,1控制点，2,2结束点)
            ctx.quadraticCurveTo(
              offsetX + hd,
              offsetY + waveHeight,
              offsetX + d,
              offsetY
            );
            ctx.quadraticCurveTo(
              offsetX + hd + d,
              offsetY - waveHeight,
              offsetX + d2,
              offsetY
            );
          }
          ctx.lineTo(startX + waveWidth, 3000);
          ctx.lineTo(startX, 3000);
          ctx.fill();
          setTimeout(move, $time / 60); //速度
        }

        move();
      },
      //雪花运动动画
      wavePlayXue() {
        // get the canvas and context
        var canvas = document.getElementById('sky');
        var ctx = canvas.getContext('2d');

        // set canvas dims to window height and width
        var W = window.innerWidth;
        var H = window.innerHeight;
        canvas.width = W;
        canvas.height = H;

        // generate snowflakes and apply attributes
        var flakesCount = 100;
        var flakes = []; // flake instances

        // loop through the empty flakes and apply attributes
        for (var i = 0; i < flakesCount; i++) {
          flakes.push({
            x: Math.random() * W,
            y: Math.random() * H,
            r: Math.random() * 5 + 2, // 2px - 7px
            d: Math.random() + 1
          });
        }

        // draw flakes onto canvas
        function drawFlakes() {
          ctx.clearRect(0, 0, W, H);
          ctx.fillStyle = '#fff';
          ctx.beginPath();
          for (var i = 0; i < flakesCount; i++) {
            var flake = flakes[i];
            ctx.moveTo(flake.x, flake.y);
            ctx.arc(flake.x, flake.y, flake.r, 0, Math.PI * 2, true);
          }
          ctx.fill();
          moveFlakes();
        }

        var angle = 0;

        function moveFlakes() {
          angle += 0.01;
          for (var i = 0; i < flakesCount; i++) {
            var flake = flakes[i];
            flake.y += Math.pow(flake.d, 2) + 1;
            flake.x += Math.sin(angle) * 2;

            if (flake.y > H) {
              flakes[i] = {x: Math.random() * W, y: 0, r: flake.r, d: flake.d};
            }
          }
        }

        setInterval(drawFlakes, 25);
      }
    }
  }
</script>

<style scoped>
  #wave {
    width: 100%;
    overflow: hidden;
    position: absolute;
    bottom: 0;
    z-index: 1;
  }

  h2 {
    text-align: center;
    color: #3a8ee6;
    font-size: 40px;
    font-weight: 300;
    text-transform: uppercase;
    line-height: 100px;
  }

  .wave-box {
    transform: scaleY(1);
    /**翻转波浪位置**/
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: -1;
  }

  canvas {
    opacity: 0.5;
    position: absolute;
    left: 0;
    top: 0;
  }
</style>
