import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environment.prod';
import { User } from './user';
import { Uloga } from './uloga';
import { Podaci } from './podaci';
import { LoginUser } from './loginUser';
import { headersToString } from 'selenium-webdriver/http';

const baseUrl = environment.url + '/korisnikUI';
//const baseUrl = environment.urlKorisnik;

const registerUrl = environment.url + '/users/signup';

@Injectable({
  providedIn: 'root'
})
export class KorisnikManagementService {

  constructor(private http: HttpClient) { }

  private async request(method: string, url: string, data?: any){
    const result = this.http.request(method, url, {
      body: data,
      responseType: 'json',
      observe: 'body'
      
    });
    return new Promise<any>((resolve, reject) => {
      result.subscribe(resolve as any, reject as any);
    });
  }

  private async request2(method: string, url: string, data?: any){
    let headers: HttpHeaders = new HttpHeaders();
        const accessToken = localStorage.getItem("id_token");
        console.log("TOKEN: " + accessToken);
        if (accessToken) {
            headers = headers.append('Authorization', 'Bearer ' + accessToken);
            headers = headers.append('Access-Control-Allow-Origin', 'http://localhost:4200');
        }
    const result = this.http.request(method, url, {
      headers: headers,
      body: data,
      responseType: 'json',
      observe: 'body'
      
    });
    return new Promise<any>((resolve, reject) => {
      result.subscribe(resolve as any, reject as any);
    });
  }


  login(loginUser: LoginUser){
    console.log(environment.url + '/token/generate-token');
    console.log(loginUser);
    return this.request('post', environment.url + '/token/generate-token',loginUser );
  }

  register(loginUser: LoginUser) {
    console.log(registerUrl);
    return this.request('post', registerUrl, loginUser);
  }

  AllUsers(){
    return this.request2('get', baseUrl + '/korisnik/getAllUsers');
  }
  oneUser(id:number){
    return this.request2('get',baseUrl+'/korisnik/'+String(id))
  }
  editUser(id:number,user: User){
    return this.request2('put',baseUrl+'/korisnik/'+String(id),user);
  }
  deleteUser(id:number){
    return this.request2('delete',baseUrl+'/korisnik/'+String(id))
  }

  allRoles(){
    return this.request2('get',baseUrl+'/uloga');
  }
  oneRole(id:number){
    return this.request2('get',baseUrl + '/uloga/'+String(id))
  }
  addRole(role:Uloga){
    return this.request2('post', baseUrl + '/uloga',role)
  }
  editRole(id:number,role:Uloga){
    return this.request2('put',baseUrl+'/uloga/'+String(id),role)
  }
  deleteRole(id:number){
    return this.request2('delete', baseUrl + '/uloga/'+String(id))
  }
  allData(){
    return this.request2('get', baseUrl + '/podaci');
  }
  oneData(id:number){
    return this.request2('get',baseUrl + '/podaci/'+String(id))
  }
  addData(role:Uloga){
    return this.request2('post', baseUrl + '/podaci',role)
  }
  editData(id:number,podaci : Podaci){
    return this.request2('put',baseUrl+'/podaci/'+String(id),podaci)
  }
  deleteData(id:number){
    return this.request2('delete', baseUrl + '/podaci/'+String(id))
  }
  


}
