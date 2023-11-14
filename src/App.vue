<script>
import axios from "axios";
// inport axios

import TutorialDataService from "./services/TutorialDataService";

// import {uuidv4} from 'uuid'

export default {
  // name: "tutorials-list",
  data() {
    return {
      operat: {
        key: "",
        value: 0,
      },
      baseURL: "http://localhost:8080/api/cal",
      baseURL1: "http://localhost:8080/api/oneOperand",
      result: "",

      exp: [""],
      listOper: [],
      toggle: true,
      empty: "0",
      flag: true,
      expression: "",
      oneOperand: "",
      operator: "",
      itr: 0,
      op: "",
      keyBoardFlag: false,
      flagModules: false,
    };
  },

  watch: {
    empty() {
      if (this.expression.length == 0) {
        this.result = "";
      }
    },
  },

  methods: {
    check() {
      for (var i = 0; i < this.listOper.length; i++) {
        console.log(this.listOper.length);
        // console.log(Number(this.exp[i + 1]) + " " + this.listOper[i]);
        if (this.listOper[i].key == "/") {
          console.log(this.expression[this.listOper[i].value + 1] + "PP");

          this.itr = this.listOper[i].value + 1;
          // let op = "";
          while (
            this.itr < this.expression.length &&
            !this.isOperator(this.expression[this.itr])
          ) {
            this.op += this.expression[this.itr];
            this.itr++;
          }

          if (parseFloat(this.op) == 0) {
            return true;
          }
          this.op = "";

          // return true;
        }
      }

      return false;
    },
    check2() {
      let len = this.expression.length - 1;

      // not valid infix
      if (
        this.expression[0] == "+" ||
        this.expression[0] == "*" ||
        this.expression[0] == "/"
      ) {
        return true;
      }
      if (
        this.expression[len] == "+" ||
        this.expression[len] == "-" ||
        this.expression[len] == "*" ||
        this.expression[len] == "/"
      ) {
        return true;
      }
      for (let i = 0; i < len; i++) {
        if (
          this.isOperator(this.expression[i]) &&
          this.isOperator(this.expression[i + 1])
        ) {
          if (
            this.expression[i] == this.expression[i + 1] &&
            this.expression[i] == "-"
          ) {
            this.expression = this.expression.replace("--", "+");
            continue;
          }
          if (
            this.isOperator(this.expression[i]) &&
            this.expression[i + 1] == "-"
          ) {
            let it = i + 2;
            while (
              it < this.expression.length &&
              this.isNumber(this.expression[it])
            ) {
              it++;
            }

            this.expression =
              this.expression.slice(0, i + 1) +
              "(0" +
              this.expression.slice(i + 1, it) +
              ")" +
              this.expression.slice(it);
            i = it;
            continue;
          }
          return true;
        }
      }
      console.log(this.expression);
      return false;
    },

    isOperator(value) {
      if (value == "+" || value == "-" || value == "*" || value == "/") {
        return true;
      }
      return false;
    },
    isNumber(val) {
      if (
        val == "0" ||
        val == "1" ||
        val == "2" ||
        val == "3" ||
        val == "4" ||
        val == "5" ||
        val == "6" ||
        val == "7" ||
        val == "8" ||
        val == "9"
      ) {
        return true;
      }
      return false;
    },
    handleKeyUp(event) {
      // Check if the Backspace key is pressed
      this.isBackspacePressed = event.keyCode === 8;
      if (this.isBackspacePressed) {
        this.flag = true;
      }
      // Check if the Enter key is pressed
    },
    chooseClick() {
      this.keyBoardFlag = true;
      const v = this.expression.slice(-1);
      if (this.isOperator(v)) {
        this.oper(v);
      } else if (this.isNumber(v)) {
        this.click(v);
      } else {
        this.flag = false;
      }
      this.keyBoardFlag = false;
    },

    changeSign() {
      if (this.isOperator(this.expression[this.expression.length - 1])) {
        this.flag = false;
        return;
      }
      this.expression =
        this.expression.slice(
          0,
          this.listOper[this.listOper.length - 1].value
        ) +
        "-" +
        this.expression.slice(this.listOper[this.listOper.length - 1].value);
    },

    oper(value) {
      if (value == "÷") {
        this.toggle = false;
        this.empty = "";
        if (!this.keyBoardFlag) this.expression += "/";

        this.listOper.push({ key: "/", value: this.expression.length - 1 });
        return;
      }
      this.toggle = false;
      this.empty = "";

      if (!this.keyBoardFlag) this.expression += value;
      this.listOper.push({ key: value, value: this.expression.length });
      // this.listOper.push(value);
    },
    click(value) {
      this.empty = "";

      if (this.toggle) {
        if (Number(this.exp[this.exp.length - 1]) > Number.MAX_SAFE_INTEGER) {
          this.flag = false;
          return;
        }
        if (!this.keyBoardFlag) this.expression += value;
        // this.exp[this.exp.length - 1] += value;
      } else {
        if (!this.keyBoardFlag) this.expression += value;
        // this.exp.push(value);
        this.toggle = true;
      }
    },

    checkIsOneOperand() {
      for (let c in this.expression) {
        if (this.isOperator(this.expression[c])) {
          return true;
        }
      }
      return false;
    },

    oneOperandClick(value) {
      if (this.checkIsOneOperand()) {
        // this.result = "Error";
        this.flag = false;
        return;
      }
      let num = parseFloat(this.expression);
      if (num == 0 && value == "1/x") {
        this.flag = false;
        return;
      } else if (num < 0 && value == "√x") {
        this.flag = false;

        return;
      }

      switch (value) {
        case "1/x":
          this.oneOperand = "in";
          break;
        case "x²":
          this.oneOperand = "sq";
          break;
        case "√x":
          this.oneOperand = "sr";
          break;
      }

      this.sendOneOperand();
    },
    modelus() {
      for (let j = 0; j < this.expression.length; j++) {
        if (this.expression[j] == "%") {
          let opernad1 = "";
          let opernad2 = "";

          for (let i = j - 1; i >= 0; i--) {
            if (this.isNumber(this.expression[i])) {
              opernad1 = this.expression[i] + opernad1;
            } else {
              if (opernad1 == "") {
                this.flag = false;
                return;
              }
              for (let g = i - 1; g >= 0; g--) {
                if (this.isNumber(this.expression[g])) {
                  opernad2 = this.expression[g] + opernad2;
                } else {
                  break;
                }
              }
              break;
            }
          }
          console.log(opernad1 + "  " + opernad2);

          if (opernad1 == "") {
            this.flag = false;
            return;
          } else if (opernad2 == "") {
            let num = parseFloat(opernad1);
            num = num / 100;
            this.expression = num.toString() + this.expression.slice(j + 1);
            console.log(this.expression);
          } else {
            let num1 = parseFloat(opernad1);
            let num2 = parseFloat(opernad2);
            num1 = (num1 * num2) / 100;
            num1 += num2;
            this.expression = num1.toString() + this.expression.slice(j + 1);
          }
          j = 0;
        }
      }
      console.log(this.expression);
    },

    async sendOneOperand() {
      try {
        const res = await axios.post(
          this.baseURL1,
          this.expression + this.oneOperand,
          {
            headers: {
              "Content-Type": "text/plain",
            },
          }
        );
        console.log(res.data);
        this.result = res.data;
      } catch (e) {
        console.log(e);
      }
    },

    clear() {
      if (this.flag == false) {
        this.flag = true;
      }
      this.result = "";
      this.empty = "0";
      this.expression = "";
      this.exp = [""];
      this.listOper = [""];
      this.toggle = true;
    },
    DeleteLastChar() {
      this.result = "";
      if (this.flag == false) {
        this.flag = true;
      }
      this.expression = this.expression.slice(0, -1);
      if (this.expression.length == 0) {
        this.empty = "0";
        this.result = "";
      }
      // this.expression=;
    },

    async equal() {
      if (this.check()) {
        this.flag = false;
        return;
      }
      let fk = this.check2();
      if (fk) {
        this.flag = false;
        return;
      }

      this.modelus();

      this.result = "";
      try {
        const response = await axios.post(this.baseURL, this.expression, {
          headers: {
            "Content-Type": "text/plain",
          },
        });
        this.result = response.data;
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    },
  },

  mounted() {
    // this.calculate();
  },
};
</script>

<template>
  <div class="container">
    <div class="calculator">
      <p v-if="!this.flag" class="msg">.Please enter valid expression</p>
      <div class="calculator__output">
        {{ result }}
      </div>
      <div class="calculator__input">
        <input
          type="text"
          v-model.trim="this.expression"
          @keydown.enter="equal()"
          @keyup="handleKeyUp"
          @input="chooseClick()"
        />
      </div>
      <div class="calculator__keys">
        <button
          class="calculator__key calculator__key--operator"
          @click="oper('%')"
        >
          %
        </button>
        <button
          class="calculator__key calculator__key--operator"
          @click="clear()"
        >
          CE
        </button>
        <button
          class="calculator__key calculator__key--operator"
          @click="clear()"
        >
          C
        </button>

        <button
          class="calculator__key calculator__key--operator"
          @click="DeleteLastChar()"
        >
          D
        </button>

        <button
          class="calculator__key calculator__key--operator"
          @click="oneOperandClick('1/x')"
        >
          1/x
        </button>
        <button
          class="calculator__key calculator__key--operator"
          @click="oneOperandClick('x²')"
        >
          x²
        </button>
        <button
          class="calculator__key calculator__key--operator"
          @click="oneOperandClick('√x')"
        >
          √x
        </button>
        <button
          class="calculator__key calculator__key--operator"
          @click="oper('÷')"
        >
          ÷
        </button>

        <button class="calculator__key" @click="click('7')">7</button>
        <button class="calculator__key" @click="click('8')">8</button>
        <button class="calculator__key" @click="click('9')">9</button>
        <button
          class="calculator__key calculator__key--operator"
          @click="oper('*')"
        >
          &times;
        </button>
        <button class="calculator__key" @click="click('4')">4</button>
        <button class="calculator__key" @click="click('5')">5</button>
        <button class="calculator__key" @click="click('6')">6</button>
        <button
          class="calculator__key calculator__key--operator"
          @click="oper('-')"
        >
          -
        </button>
        <button class="calculator__key" @click="click('1')">1</button>
        <button class="calculator__key" @click="click('2')">2</button>
        <button class="calculator__key" @click="click('3')">3</button>

        <button
          class="calculator__key calculator__key--operator"
          @click="oper('+')"
        >
          +
        </button>
        <button
          class="calculator__key calculator__key--operator"
          @click="changeSign()"
        >
          +/-
        </button>
        <button class="calculator__key" @click="click('0')">0</button>
        <button class="calculator__key" @click="click('.')">.</button>
        <!-- <button class="calculator__key" @click="clear()">AC</button> -->

        <button class="calculator__key calculator__key--enter" @click="equal()">
          =
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
} */
.container {
  display: grid;
  height: 45rem;
  justify-content: center;
  align-items: center;
}

.calculator {
  color: hsl(202, 11%, 29%);
  max-inline-size: 22rem;
  border-radius: 10px;
  overflow: hidden;

  box-shadow: 0px 3px 6px 0px rgba(0, 0, 0, 0.15),
    0px 2px 4px 0px rgba(0, 0, 0, 0.12); /*still not understand Give the calculator some depth off the page */
}
.msg {
  font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
    "Lucida Sans", Arial, sans-serif;
  color: red;
  font-size: 0.9rem;
  text-align: start;
  margin-bottom: 0px;
  /* padding-bottom: 0.2rem; */
}

input {
  border-color: hsl(207, 14%, 27%);
  background: hsl(207, 14%, 27%);
  width: 100%; /* Make the input take up the entire width of its container */

  text-align: end;
  box-sizing: border-box;
  font-size: 2rem;
  color: white;

  padding-top: 1rem;
  padding-bottom: 1rem;
  padding-right: 0.75rem;
}
.calculator__input {
  background: hsl(207, 14%, 27%);
}
.calculator__output {
  font-size: 2rem;
  text-align: start;
  background: hsl(207, 14%, 27%);
  margin-top: 0px;

  color: white;
  /* padding-top: 1rem; */
  padding-bottom: 1rem;
  padding-left: 0.75rem;
  padding-right: 0.75rem;
}
.calculator__keys {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 1px;
  background: hsl(207, 14%, 27%);
}

.calculator__key {
  background: hsl(206, 17%, 27%); /* Change the keys to a darker gray */
  color: hsl(255, 100%, 100%);
  border: none;
  font-size: 2rem;

  inline-size: 60px;
  block-size: 60px;
  border-radius: 80px;
  margin: 0.7rem;
  cursor: pointer;

  /* padding-block-start: 0.5rem;
   /* Logical Property for padding-top */
  /* padding-block-end: 0.5rem;  */
  /* Logical Property for padding-bottom */
  /* padding-inline-start: 1.25rem; */
  /* Logical Property for padding-left */
  /* padding-inline-end: 1.25rem;  */
  /* Logical Property for padding-right */
}

.calculator__key:active {
  box-shadow: 0 0 8px 0 rgba(0, 0, 0, 0.3) inset;
  background-color: rgb(138, 138, 181);
  outline: none;
}
.calculator__key--enter,
.calculator__key--operator {
  background-color: hsl(257, 83%, 9%);
  color: white;
}

.calculator__key--operator:active {
  background: hsl(207, 36%, 61%);
}

.calculator__key--enter {
  /* grid-column: 4 / span 1; */
  /* grid-row: 2/6;  */
  /*grid-row:2/span 4;*/
  background-color: hsl(6, 61%, 73%);
  /* height: auto;  */
  /* Allow the key to span the four rows */
}
</style>
