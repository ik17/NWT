import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment.prod';
import { User } from './user';
import { Uloga } from './uloga';
import { Podaci } from './podaci';
import { LoginUser } from './loginUser';

//const baseUrl = environment.url + '/korisnikUI/';
const baseUrl = environment.urlKorisnik;

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

  register(loginUser: LoginUser) {
    return this.request('post', registerUrl, loginUser);
  }

  AllUsers(){
    return this.request('get', baseUrl + '/korisnik/getAllUsers');
  }
  oneUser(id:number){
    return this.request('get',baseUrl+'/korisnik/'+String(id))
  }
  editUser(id:number,user: User){
    return this.request('put',baseUrl+'/users/'+String(id),user);
  }
  deleteUser(id:number){
    return this.request('delete',baseUrl+'/users/'+String(id))
  }

  allRoles(){
    return this.request('get',baseUrl+'/uloga');
  }
  oneRole(id:number){
    return this.request('get',baseUrl + '/uloga/'+String(id))
  }
  addRole(role:Uloga){
    return this.request('post', baseUrl + '/uloga',role)
  }
  editRole(id:number,role:Uloga){
    return this.request('put',baseUrl+'/uloga/'+String(id),role)
  }
  deleteRole(id:number){
    return this.request('delete', baseUrl + '/uloga/'+String(id))
  }
  allData(){
    return this.request('get', baseUrl + '/podaci');
  }
  oneData(id:number){
    return this.request('get',baseUrl + '/podaci/'+String(id))
  }
  addData(role:Uloga){
    return this.request('post', baseUrl + '/podaci',role)
  }
  editData(id:number,podaci : Podaci){
    return this.request('put',baseUrl+'/podaci/'+String(id),podaci)
  }
  deleteData(id:number){
    return this.request('delete', baseUrl + '/podaci/'+String(id))
  }
  


}
