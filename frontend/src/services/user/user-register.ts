import axios from "axios";
import router from "@/router";

export function signup(email: string, password: string, role:string) {
  const user = {
    email: email,
    password: password,
    role: role
  };

  return axios.post("/signup", JSON.stringify(user), {
    headers: {
      "Content-Type": "application/json"
    }
  })
    .then(response => {
      if (response.status === 200) {
        router.replace("/login");
        return "회원가입이 완료되었습니다. 로그인 화면으로 이동합니다.";
      }
    })
    .catch(error => {
      if (error.response.status === 400) {
        return "이미 존재하는 이메일입니다.";
      }
    });
}

export function validateEmail(email: string) {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  return !emailRegex.test(email);
}

export function validatePassword(password: string) {
  const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$/;
  return !passwordRegex.test(password);
}
