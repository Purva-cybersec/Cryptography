#include <iostream>
#include <sstream>
#include <iomanip>
#include <openssl/sha.h>
#include <random>

std::string truncated_hash(const std::string &input) {
    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_CTX sha256;
    SHA256_Init(&sha256);
    SHA256_Update(&sha256, input.c_str(), input.size());
    SHA256_Final(hash, &sha256);

    std::stringstream ss;
    for (int i = 0; i < 4; ++i) {
        ss << std::hex << std::setw(2) << std::setfill('0') << (int)hash[i];
    }
    return ss.str();
}
std::string full_hash(const std::string &input) {
    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_CTX sha256;
    SHA256_Init(&sha256);
    SHA256_Update(&sha256, input.c_str(), input.size());
    SHA256_Final(hash, &sha256);

    std::stringstream ss;
    for (int i = 0; i < SHA256_DIGEST_LENGTH; ++i) {
        ss << std::hex << std::setw(2) << std::setfill('0') << (int)hash[i];
    }
    return ss.str();
}
std::string common_hash_part(const std::string &hash1, const std::string &hash2) {
    std::string commonPart;
    size_t minLength = std::min(hash1.length(), hash2.length());

    for (size_t i = 0; i < minLength; ++i) {
        if (hash1[i] == hash2[i]) {
            commonPart += hash1[i];
        } else {
            break; // Stop at the first mismatch
        }
    }

    return commonPart;
}

int main() {
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> dis(1, 100000000);

    std::string x = std::to_string(dis(gen));
    std::string tort = x, hare = x, prev_hare, prev_tort;
    int counter = 0;

    while (counter < (1 << 20)) {
        tort = truncated_hash(tort);
        hare = truncated_hash(truncated_hash(hare));
        counter++;
        if (tort == hare) {
break;
        }
    }

    if (counter == (1 << 20)) {
        std::cout << "No collision found." << std::endl;
        return 0;
    }
tort = x;
    prev_hare = hare;
    prev_tort = x;
    while (tort != hare) {
        prev_hare = hare;
        prev_tort = tort;
        tort = truncated_hash(tort);
        hare = truncated_hash(hare);
    }

    std::cout << "-----" << std::endl;
    std::cout << "X = " << prev_hare << std::endl;
    std::cout << "X' = " << prev_tort << std::endl;
    std::cout << "H(X) = " << truncated_hash(prev_tort) << std::endl;
    std::cout << "H(X')= " << truncated_hash(prev_hare) << std::endl;    
    std::string fullHashTort = full_hash(prev_tort);
    std::string fullHashHare = full_hash(prev_hare);
    std::cout << "SHA-256(X) = " << fullHashTort << std::endl;
    std::cout << "SHA-256(X') = " << fullHashHare << std::endl;
    
    std::string commonPart = common_hash_part(fullHashTort, fullHashHare);
    std::cout << "Truncated Hash: " << commonPart << std::endl;

return 0;
}