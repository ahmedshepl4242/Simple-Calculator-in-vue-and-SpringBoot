import http from "../http-common";

class TutorialDataService {
  getAll() {
    return http.get("/api");
  }

  get(num1,num2) {
    return http.get("api/:{num1}/:{num2}");
  }

  create(data) {
    return http.post("/api", data);
  }

  update(id, data) {
    return http.put(`/${id}`, data);
  }

  delete(id) {
    return http.delete(`/${id}`);
  }

  deleteAll() {
    return http.delete();
  }

  findByTitle(title) {
    return http.get(`?title=${title}`);
  }
}

export default new TutorialDataService();
