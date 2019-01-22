import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductSku } from 'app/shared/model/product-sku.model';

type EntityResponseType = HttpResponse<IProductSku>;
type EntityArrayResponseType = HttpResponse<IProductSku[]>;

@Injectable({ providedIn: 'root' })
export class ProductSkuService {
    public resourceUrl = SERVER_API_URL + 'api/product-skus';

    constructor(protected http: HttpClient) {}

    create(productSku: IProductSku): Observable<EntityResponseType> {
        return this.http.post<IProductSku>(this.resourceUrl, productSku, { observe: 'response' });
    }

    update(productSku: IProductSku): Observable<EntityResponseType> {
        return this.http.put<IProductSku>(this.resourceUrl, productSku, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductSku>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductSku[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
