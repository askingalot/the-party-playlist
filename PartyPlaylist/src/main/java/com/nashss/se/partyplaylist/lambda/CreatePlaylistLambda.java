package com.nashss.se.partyplaylist.lambda;
import com.nashss.se.partyplaylist.activity.requests.CreatePlaylistRequest;
import com.nashss.se.partyplaylist.activity.results.CreatePlaylistResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class CreatePlaylistLambda
        extends LambdaActivityRunner<CreatePlaylistRequest, CreatePlaylistResult>
        implements RequestHandler<LambdaRequest<CreatePlaylistRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreatePlaylistRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromBody(CreatePlaylistRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideCreatePlaylistActivity().handleRequest(request)
    );
    }
}
