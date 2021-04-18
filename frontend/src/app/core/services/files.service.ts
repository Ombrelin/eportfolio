import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {API_URL} from "../constants/api-constants";

@Injectable({
  providedIn: 'root'
})
export class FilesService {

  constructor(
    private http: HttpClient
  ) {
  }

  async upload(authString : string, file: File) {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return await this.http.post(`${API_URL}/files`, formData,
      {
        responseType: 'text',
        headers: new HttpHeaders({
          Authorization: authString
        })
      }
    ).toPromise();
  }

}
