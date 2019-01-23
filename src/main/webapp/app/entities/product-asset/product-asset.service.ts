import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductAsset } from 'app/shared/model/product-asset.model';

type EntityResponseType = HttpResponse<IProductAsset>;
type EntityArrayResponseType = HttpResponse<IProductAsset[]>;

@Injectable({ providedIn: 'root' })
export class ProductAssetService {
    public resourceUrl = SERVER_API_URL + 'api/product-assets';

    constructor(protected http: HttpClient) {}

    create(productAsset: IProductAsset): Observable<EntityResponseType> {
        return this.http.post<IProductAsset>(this.resourceUrl, productAsset, { observe: 'response' });
    }

    update(productAsset: IProductAsset): Observable<EntityResponseType> {
        return this.http.put<IProductAsset>(this.resourceUrl, productAsset, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductAsset>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductAsset[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
