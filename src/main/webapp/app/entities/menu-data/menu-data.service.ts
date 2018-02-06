import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MenuData } from './menu-data.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MenuDataService {

    private resourceUrl = SERVER_API_URL + 'api/menu-data';
    private resourceRootUrl = SERVER_API_URL + 'api/_root/menu-data';

    constructor(private http: Http) { }

    create(menuData: MenuData): Observable<MenuData> {
        const copy = this.convert(menuData);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(menuData: MenuData): Observable<MenuData> {
        const copy = this.convert(menuData);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MenuData> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to MenuData.
     */
    private convertItemFromServer(json: any): MenuData {
        const entity: MenuData = Object.assign(new MenuData(), json);
        return entity;
    }

    /**
     * Convert a MenuData to a JSON which can be sent to the server.
     */
    private convert(menuData: MenuData): MenuData {
        const copy: MenuData = Object.assign({}, menuData);
        return copy;
    }

    findRootMenuData() :Observable<MenuData[]>{
        return this.http.get(this.resourceRootUrl).map((res: Response) => {
            console.log(JSON.stringify(res.json()));
            return res.json();
        });
    }
}
