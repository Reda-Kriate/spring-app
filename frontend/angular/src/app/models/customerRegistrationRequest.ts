export interface customerRegistrationRequest{
  name?:string,
  age?:number,
  email?:string,
  password?:string,
  gender?: "MALE" | "FEMALE",
}
