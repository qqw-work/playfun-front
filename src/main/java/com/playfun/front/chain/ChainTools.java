package com.playfun.front.chain;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.Utils;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainTools {

    public static String ERC20_TRANSFER_EVENT_SIGN() {
        List<TypeReference<?>> inParam = new ArrayList<>();
        inParam.add(TypeReference.create(Address.class));
        inParam.add(TypeReference.create(Address.class));
        inParam.add(TypeReference.create(Uint256.class));

        return EventEncoder.encode(new Event("Transfer", inParam));
    }

    public static List<TypeReference<Type>> ERC20_TRANSFER_EVENT_NON_INDEX_PARAM() {
        List<TypeReference<?>> resultList = Arrays.asList(TypeReference.create(Uint256.class));
        return Utils.convert(resultList);
    }

    public static BigInteger ERC20_TRANSFER_EVENT_DATA_TRANSFORM(String data) {
        List<Type> decode = FunctionReturnDecoder.decode(data, ERC20_TRANSFER_EVENT_NON_INDEX_PARAM());
        if(decode.size() != 1 || !decode.get(0).getTypeAsString().equals("uint256")) return BigInteger.ZERO;

        return (BigInteger) decode.get(0).getValue();
    }
}
