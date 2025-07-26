export interface CustomerDTO{
  id?:number,
  name?:string,
  age?:number,
  email?:string,
  gender?: "MALE" | "FEMALE",
  roles:string[],
  username?:string
}
