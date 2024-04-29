import CryptoJS from 'crypto-js';

export function encryption(src) {
    const key = CryptoJS.enc.Utf8.parse("thanks,pig4cloud");
    const encrypted = CryptoJS.AES.encrypt(src, key, {
        iv: key,
        mode: CryptoJS.mode.CFB,
        padding: CryptoJS.pad.NoPadding,
    });
    return encrypted.toString();
}
