// import "./assets";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount("#app");
//  methods: {
    // retrieveTutorials() {
    //   TutorialDataService.getAll()
    //     .then((response) => {
    //       this.res = response.data;
    //       console.log(response.data);
    //     })
    //     .catch((e) => {
    //       console.log(e);
    //     });
    // },
    // const data = {
    //   op1: 55,
    //   op2: 22,
    //   // Other map data fields
    // };
    //   axios
    //     .post("http://localhost:8080/api", { op1: 50, op2: 10 })
    //     .then((response) => {
    //       console.log(response.data);
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // },
    // <Appointment :nameOfDocter="put Name of your Component her " :Department="put Department of your Component her">
    // async makePostRequest() {
    //   try {
    //     const response = await axios.post(
    //       "http://localhost:8080/api/save",
    //       this.operands
    //     );
    //     this.res = response.data;
    //     console.log("Response from the server:", this.res);
    //   } catch (error) {
    //     this.error = error;
    //     console.error("Error making POST request:", error);
    //   }
    // },
    // sendOperand(){
    //   console.log("dsffffff");
    //   TutorialDataService.sendOperand(this.operands).then((response) => {
    //     this.res = response.data;
    //     console.log(response.data);
    //   });
    // },
    // refreshList() {
    //   this.retrieveTutorials();
    //   this.currentTutorial = null;
    //   this.currentIndex = -1;
    // },
    // setActiveTutorial(tutorial, index) {
    //   this.currentTutorial = tutorial;
    //   this.currentIndex = tutorial ? index : -1;
    // },
    // removeAllTutorials() {
    //   TutorialDataService.deleteAll()
    //     .then(response => {
    //       console.log(response.data);
    //       this.refreshList();
    //     })
    //     .catch(e => {
    //       console.log(e);
    //     });
    // },
    // searchTitle() {
    //   TutorialDataService.findByTitle(this.title)
    //     .then(response => {
    //       this.tutorials = response.data;
    //       this.setActiveTutorial(null);
    //       console.log(response.data);
    //     })
    //     .catch(e => {
    //       console.log(e);
    //     });
    // }
//  },