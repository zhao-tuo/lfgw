import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { SaleAmt1 } from './sale-amt-1.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SaleAmt1Service {

    private resourceUrl = SERVER_API_URL + 'api/sale-amt-1-s';

    constructor(private http: Http) { }

    create(saleAmt1: SaleAmt1): Observable<SaleAmt1> {
        const copy = this.convert(saleAmt1);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(saleAmt1: SaleAmt1): Observable<SaleAmt1> {
        const copy = this.convert(saleAmt1);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SaleAmt1> {
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
     * Convert a returned JSON object to SaleAmt1.
     */
    private convertItemFromServer(json: any): SaleAmt1 {
        const entity: SaleAmt1 = Object.assign(new SaleAmt1(), json);
        return entity;
    }

    /**
     * Convert a SaleAmt1 to a JSON which can be sent to the server.
     */
    private convert(saleAmt1: SaleAmt1): SaleAmt1 {
        const copy: SaleAmt1 = Object.assign({}, saleAmt1);
        return copy;
    }
}
