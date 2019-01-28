import { IProduct } from 'app/shared/model/product.model';

export const enum GenderType {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    UNISEX = 'UNISEX'
}

export const enum ProductSize {
    UNIQUE = 'UNIQUE',
    XXSMALL = 'XXSMALL',
    XSMALL = 'XSMALL',
    SMALL = 'SMALL',
    MEDIUM = 'MEDIUM',
    LARGE = 'LARGE',
    XLARGE = 'XLARGE',
    XXLARGE = 'XXLARGE'
}

export interface IProductSku {
    id?: number;
    gender?: GenderType;
    size?: ProductSize;
    product?: IProduct;
}

export class ProductSku implements IProductSku {
    constructor(public id?: number, public gender?: GenderType, public size?: ProductSize, public product?: IProduct) {}
}
