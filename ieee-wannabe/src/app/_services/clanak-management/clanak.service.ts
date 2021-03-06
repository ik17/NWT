import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environment.prod';
import { Autor } from './autor';
import { Kategorija } from './kategorija';
import { Clanak } from './clanak';
import { Komentar } from './komentar';
import { Korisnik } from './korisnik';

const baseUrl = environment.url + '/clanakUI';
const clanakUrl = environment.urlClanak;

@Injectable({
  providedIn: 'root'
})
export class ClanakService {

  constructor(private http: HttpClient) { }

  private async request(method: string, url: string, data?: any){

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

  //link
  
  getLink(naziv:string) {
	  return this.request('get', baseUrl + '/clanak/link/' + naziv);
  }
  //servisi za pretragu 
  allClanakByNaziv(naziv:string) {
    return this.request('get', baseUrl + '/clanak/naziv/'+ naziv);
  }
  allClanakByKategorija(naziv:string) {
    return this.request('get', baseUrl + '/clanak/kategorija/' + naziv);
  }


  //korisnik
  AllUsers(){
    return this.request('get', baseUrl + '/korisnik');
  }
  oneUser(id:number){
    return this.request('get',baseUrl+'/korisnik/'+String(id))
  }
  editUser(id:number,user: Korisnik){
    return this.request('put',baseUrl+'/korisnik/'+String(id),user);
  }
  deleteUser(id:number){
    return this.request('delete',baseUrl+'/korisnik/'+String(id))
  }
  createUser(user: Korisnik) {
    return this.request('post', clanakUrl + '/korisnik',user);
  }

  //kategorija
  AllKategorija(){
    return this.request('get', baseUrl + '/kategorija');
  }
  oneKategorija(id:number){
    return this.request('get',baseUrl+'/kategorija/'+String(id))
  }
  editKategorija(id:number,user: Kategorija){
    return this.request('put',baseUrl+'/kategorija/'+String(id),user);
  }
  deleteKategorija(id:number){
    return this.request('delete',baseUrl+'/kategorija/'+String(id))
  }
  createKategorija(user: Kategorija) {
    return this.request('post', baseUrl + '/kategorija', user);
  }

  //clanak
  AllClanak(){
    return this.request('get', baseUrl + '/clanak');
  }
  oneClanak(id:number){
    return this.request('get',baseUrl+'/clanak/'+String(id))
  }
  editClanak(id:number,user: Clanak){
    return this.request('put',baseUrl+'/clanak/'+String(id),user);
  }
  deleteClanak(id:number){
    return this.request('delete',baseUrl+'/clanak/'+String(id))
  }
  createClanak(user: Clanak) {
    return this.request('post', baseUrl + '/clanak',user);
  }

  //autori
  AllAutor(){
    return this.request('get', baseUrl + '/autor');
  }
  oneAutor(id:number){
    return this.request('get',baseUrl+'/autor/'+String(id))
  }
  editAutor(id:number,user: Autor){
    return this.request('put',baseUrl+'/autor/'+String(id),user);
  }
  deleteAutor(id:number){
    return this.request('delete',baseUrl+'/autor/'+String(id))
  }
  createAutor(user: Autor) {
    return this.request('post', baseUrl + '/autor',user);
  }
  autorByClanak(id:number) {
    return this.request('get', baseUrl + '/autor/clanak/' + String(id));
  }


  //komentari
  AllKomentar(){
    return this.request('get', baseUrl + '/komentar');
  }
  oneKomentar(id:number){
    return this.request('get',baseUrl+'/komentar/'+String(id))
  }
  editKomentar(id:number,user: Komentar){
    return this.request('put',baseUrl+'/komentar/'+String(id),user);
  }
  deleteKomentar(id:number){
    return this.request('delete',baseUrl+'/komentar/'+String(id))
  }
  createKomentar(user: Komentar) {
    return this.request('post', baseUrl + '/komentar',user);
  }
  komentarByClanak(id:number) {
	  return this.request('get', baseUrl+ '/komentar/clanak/' + String(id));
  }
  getArticleByAuthorId(id:number){
    return this.request('get', baseUrl+ '/clanak/authorId/' + String(id));
  }



}
