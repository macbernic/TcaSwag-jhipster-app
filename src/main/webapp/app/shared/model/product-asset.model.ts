import { IProduct } from 'app/shared/model/product.model';

export interface IProductAsset {
    id?: number;
    title?: string;
    assetContentType?: string;
    asset?: any;
    product?: IProduct;
}

export class ProductAsset implements IProductAsset {
    constructor(
        public id?: number,
        public title?: string,
        public assetContentType?: string,
        public asset?: any,
        public product?: IProduct
    ) {}
}
